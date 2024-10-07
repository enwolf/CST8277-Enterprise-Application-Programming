/**
 * This file contains the implementation of the ListDataDao interface, which provides
 * data access functionality for retrieving lists of specialty data from the database.
 * The class uses JDBC to perform database operations, managing connections and 
 * prepared statements in a session or request-scoped environment.
 * 
 * The file also includes lifecycle methods for establishing and closing database connections 
 * using the Jakarta EE annotations {@code @PostConstruct} and {@code @PreDestroy}.
 * Logging is performed via the servlet context's logging mechanism to track 
 * significant events in the application flow.
 * 
 * @file ListDataDaoImpl.java
 * @version 1.0
 * @since 2024-10-04
 * @author Robin Phillis
 */
package databank.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

import javax.sql.DataSource;

/**
 * Implementation of the {@link ListDataDao} interface, responsible for performing 
 * data retrieval operations for specialty data from the database. This class is 
 * request-scoped, meaning a new instance is created for each HTTP request.
 * 
 * The class establishes a connection to the database using JDBC and retrieves
 * specialty information via SQL queries. It manages the connection and 
 * PreparedStatements lifecycle through the use of {@code @PostConstruct} 
 * and {@code @PreDestroy} annotations to ensure resources are properly 
 * allocated and released.
 * 
 * Logging is done using the servlet context's logging facility, which helps in 
 * diagnosing errors and tracking significant actions in the code.
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 2024-10-04
 */
@Named
@RequestScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {

	private static final long serialVersionUID = 1L;
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";
	private static final String READ_ALL_SPECIALTIES = "SELECT name FROM specialty";

	@Inject
	protected ExternalContext externalContext;

	@Resource(name = "java:app/jdbc/databank")
	protected DataSource databankDS;
	protected Connection connectionToDatabase;
	protected PreparedStatement readAllSpecialtiesPreparedStatement;

	/**
	 * Logs messages to the server log using the {@link ServletContext}.
	 * 
	 * @param msg the message to be logged.
	 */
	private void logMsg(String msg) 
	{
		((ServletContext) externalContext.getContext()).log(msg);
	}

	/**
	 * Initializes the database connection and prepares the SQL statement 
	 * for reading all specialties from the database. This method is 
	 * annotated with {@code @PostConstruct} to ensure it is executed 
	 * after the bean's construction and dependency injection.
	 */
	@PostConstruct
	protected void buildConnectionAndStatements() 
	{
	    try 
	    {
	        logMsg("Attempting to get connection from data source.");
	        connectionToDatabase = databankDS.getConnection();
	        logMsg("Connection established successfully.");
	        
	        readAllSpecialtiesPreparedStatement = connectionToDatabase.prepareStatement(READ_ALL_SPECIALTIES);
	        logMsg("PreparedStatement initialized successfully.");

	    } 
	    catch (Exception e) 
	    {
	        logMsg("Something went wrong getting connection from database: " + e.getLocalizedMessage());
	        e.printStackTrace();  
	    }
	}

	/**
	 * Closes the database connection and the PreparedStatement. This method is 
	 * annotated with {@code @PreDestroy} to ensure that resources are released 
	 * before the bean is destroyed at the end of the request lifecycle.
	 */
	@PreDestroy
	protected void closeConnectionAndStatements() 
	{
		try 
		{
			logMsg("closing stmts and connection");
			
			if (readAllSpecialtiesPreparedStatement != null) 
				readAllSpecialtiesPreparedStatement.close();
			
			if (connectionToDatabase != null)
				connectionToDatabase.close();
		} 
		catch (Exception e) 
		{
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	/**
	 * Reads all specialties from the database by executing the 
	 * {@code readAllSpecialtiesPreparedStatement}. It returns 
	 * a list of specialties' names as strings.
	 * 
	 * @return a list of strings representing the specialties.
	 */
	@Override
	public List<String> readAllSpecialties() 
	{
	    logMsg("reading all specialties");
	    List<String> specialties = new ArrayList<>();
	
	    try (ResultSet resultSet = readAllSpecialtiesPreparedStatement.executeQuery()) 
	    {
	        while (resultSet.next()) 
	        {
	            specialties.add(resultSet.getString("name"));  // Correct column name
	        }
	    } 
	    catch (SQLException e) 
	    {
	        logMsg("Error reading specialties: " + e.getLocalizedMessage());
	    }
	    return specialties;
	}
}
