/*********************************************************************************************************
 * File:  GenerateRandomPhysicianRecords.java Course Materials CST8277
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 2024-09-14
 * 
 * @description This class generates random physician records and inserts them into a database. 
 *              It uses the Picocli library for command-line argument parsing, the Podam library for 
 *              generating random data, and SLF4J for logging. The class includes methods to 
 *              truncate the existing data in the physician table, insert new records, and log 
 *              information about the generated records.
 * 
 * @see picocli.CommandLine
 * @see uk.co.jemos.podam.api.PodamFactory
 * @see uk.co.jemos.podam.api.PodamFactoryImpl
 * @see uk.co.jemos.podam.api.ClassInfoStrategy
 * @see uk.co.jemos.podam.api.DefaultClassInfoStrategy
 * @see org.slf4j.Logger
 * @see org.slf4j.LoggerFactory
 * @see java.sql.Connection
 * @see java.sql.DriverManager
 * @see java.sql.PreparedStatement
 * @see java.sql.ResultSet
 * @see java.sql.SQLException
 * @see java.sql.Statement
 * @see java.time.Duration
 * @see java.time.Instant
 * @see java.util.Properties
 */

package jdbccmd;

import java.io.PrintWriter;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jdbccmd.LoggingOutputStream.LogLevel;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParameterException;
import uk.co.jemos.podam.api.ClassInfoStrategy;
import uk.co.jemos.podam.api.DefaultClassInfoStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * A command-line application that generates random physician records and inserts them into a database.
 * <p>
 * This class uses Picocli for command-line parsing and the Podam library for generating random data.
 * It logs the generated data and timing information using SLF4J.
 * </p>
 */
@Command(description = "Generate random physicians", name = "jdbccmd.GenerateRandomPhysicianRecords")
public class GenerateRandomPhysicianRecords {
	
	/** The current class type */
	protected static final Class<?> MY_KLASSNAME = MethodHandles.lookup().lookupClass();
	
	/** Logger for the class */
	private static final Logger logger = LoggerFactory.getLogger(MY_KLASSNAME);

	/** Error message for command-line parsing failures */
	protected static final String CMDLINE_PARSING_ERROR_MSG = "cmdLine parsing error:  {}";
	
	/** Message for elapsed time logging */
	protected static final String ELAPSED_TIME_MSG = "Elapsed time = {} ms";
	
	/** SQL statement to truncate the physician table */
	private static final String TRUNC_PHYSICIAN = "TRUNCATE TABLE PHYSICIAN";
	
	/** SQL statement to insert a physician record */
	protected static final String INSERT_PHYSICIAN = "INSERT INTO PHYSICIAN(LAST_NAME, FIRST_NAME, EMAIL, PHONE, SPECIALTY, CREATED) VALUES (?, ?, ?, ?, ?, now())";

	/**
	 * Main method to run the application.
	 * <p>
	 * Parses command-line arguments, generates physician records, and writes them to the database.
	 * </p>
	 * 
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) 
	{
		CmdLineOptions cmdLineOptions = new CmdLineOptions();
		CommandLine cmdLine = new CommandLine(cmdLineOptions);
		cmdLine.setCommandName(MY_KLASSNAME.getName());
		
		try 
		{			
			cmdLine.parseArgs(args);			
		}
		catch (ParameterException e) 
		{
			logger.error(CMDLINE_PARSING_ERROR_MSG, e.getLocalizedMessage());
			logCmdLineUsage(e.getCommandLine(), LogLevel.ERROR);
			System.exit(-1);			
		}
		
		if (cmdLineOptions.helpRequested) 
		{
			logCmdLineUsage(cmdLine, LogLevel.INFO);			
		} 
		else 
		{
			generatePhysicians(cmdLineOptions.jdbcUrl, cmdLineOptions.username, cmdLineOptions.password,
					cmdLineOptions.count);
		}
	}

	/**
	 * Generates random physician records and inserts them into the database.
	 * 
	 * @param jdbcUrl The JDBC URL of the database
	 * @param username The username for the database connection
	 * @param password The password for the database connection
	 * @param genCount The number of physician records to generate
	 */
	public static void generatePhysicians(String jdbcUrl, String username, String password, int genCount) 
	{
		Instant startTime = Instant.now();

		Properties dbProps = new Properties();
		dbProps.put("user", username);
		dbProps.put("password", password);

		try ( 
        	Connection connection = DriverManager.getConnection(jdbcUrl, dbProps);
        	PreparedStatement pstmtTrunc = connection.prepareStatement(TRUNC_PHYSICIAN);        	
        	PreparedStatement pstmtInsert = connection.prepareStatement(INSERT_PHYSICIAN, Statement.RETURN_GENERATED_KEYS);
        )
        {	
        	pstmtTrunc.executeUpdate();
        	
			PodamFactory factory = new PodamFactoryImpl();
			ClassInfoStrategy classInfoStrategy = factory.getClassStrategy();
			((DefaultClassInfoStrategy) classInfoStrategy).addExcludedField(Physician.class, "id");
			factory.getStrategy().addOrReplaceTypeManufacturer(String.class, new PhysicianManufacturer());
			
			logger.info("==========================================================================  PHYSICIAN DATA ==========================================================================");
			logger.info("| ID  | Last Name  | First Name | Email                       | Phone Number  | Specialty                                     | Created                             |");
			logger.info("=====================================================================================================================================================================");
			
			for (int cnt = 0, numRandomPhysicians = genCount; cnt < numRandomPhysicians; cnt++) 
			{
				Physician randomPhysician = factory.manufacturePojoWithFullData(Physician.class);
				String formattedPhoneNumber = formatPhoneNumber(randomPhysician.getPhoneNumber());
				
				pstmtInsert.setString(1, randomPhysician.getLastName());
				pstmtInsert.setString(2, randomPhysician.getFirstName());
				pstmtInsert.setString(3, randomPhysician.getEmail());
				pstmtInsert.setString(4, formattedPhoneNumber);
				pstmtInsert.setString(5, randomPhysician.getSpecialty());
				
				pstmtInsert.execute();
				
				try (ResultSet generatedKeys = pstmtInsert.getGeneratedKeys()) 
				{
					if (generatedKeys.next()) 
					{
						int id = generatedKeys.getInt(1); 
						randomPhysician.setId(id);
						logger.debug("created random physician \r\n\t{}", randomPhysician);
					
						String logMessage = String.format(
						    "| %-3s | %-10s | %-10s | %-27s | %-13s | %-45s | %-35s |", 
						    id,
						    randomPhysician.getLastName(),
						    randomPhysician.getFirstName(),
						    randomPhysician.getEmail(),
						    formattedPhoneNumber,
						    randomPhysician.getSpecialty(),
						    randomPhysician.getCreated().toString()
						);

						logger.info(logMessage);
					}
					else 
					{
						logger.error("could not retrieve generated PK");
					}
				}				
			}
			logger.info("|                                                                                                                                                                   |"); 
		} 
        catch (SQLException e) 
        {
			logger.error("something went wrong inserting new physician, ", e);
		}

		Instant endTime = Instant.now();
		long elapsedTime = Duration.between(startTime, endTime).toMillis();
		logger.info("| " + ELAPSED_TIME_MSG + "                                                                                                                                             |", elapsedTime);
		logger.info("|                                                                                                                                                                   |"); 
		logger.info("=====================================================================================================================================================================");
	}

	/**
	 * Prints the content of the command-line usage to the logger.
	 * 
	 * @param cmdLine The CommandLine instance used for parsing
	 * @param level The log level for outputting the usage information
	 */
	protected static void logCmdLineUsage(CommandLine cmdLine, LogLevel level) 
	{		
		LoggingOutputStream los = new LoggingOutputStream(logger, level);
		PrintWriter pw = new PrintWriter(los);
		cmdLine.usage(pw);
		pw.flush();
		los.line();
	}

	/**
	 * Formats a 10-digit phone number to the XXX-XXX-XXXX format.
	 * 
	 * If the phone number has exactly 10 digits, it is formatted into three groups 
	 * separated by hyphens. Otherwise, the original number is returned.	 * 
	 * 
	 * @param phoneNumber The phone number to format.
	 * @return The formatted phone number (XXX-XXX-XXXX), or the original input if it's not 10 digits.
	 */
	public static String formatPhoneNumber(String phoneNumber) 
	{
		// The pattern (\\d{3})(\\d{3})(\\d+) captures 3 groups: the first three digits, 
	    // the next three digits, and the remaining digits. "$1-$2-$3" refers to these groups, 
	    // inserting hyphens between them.
		
    if (phoneNumber != null && phoneNumber.length() == 10) 
	    {
	        return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d+)", "$1-$2-$3");
	    }
	    else 
	    {
	        return phoneNumber; // return the original phone number if it doesn't match the expected length
	    }
	}
}
