/*********************************************************************************************************
 * File:  PhysicianDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

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
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.PhysicianPojo;

@SuppressWarnings("unused")
/**
 * Description:  Implements the C-R-U-D API for the database
 */
//TODO Don't forget this is a managed bean with an application scope
@Named
@SessionScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//TODO Set the value of this string constant properly.  This is the JNDI name
	//     for the data source.
	private static final String DATABANK_DS_JNDI = "java:comp/env/jdbc/H2Pool"; //I hope this is correct?
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve the list of physicians from the database.
	private static final String READ_ALL = "SELECT id, last_name, first_name, email, phone, specialty, created "
							             + "FROM physician";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve a physician by ID from the database.
	
	private static final String READ_PHYSICIAN_BY_ID = "SELECT id, last_name, first_name, email, phone, specialty  "
													 + "FROM physician "
													 + "WHERE ID = ?";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to insert a new physician to the database.
	private static final String INSERT_PHYSICIAN = "INSERT INTO physician ( last_name, first_name, email, phone, specialty, created) "
												 + "VALUES (?, ?, ?, ?, ?, ?)";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to update the fields of a physician in the database.
	private static final String UPDATE_PHYSICIAN_ALL_FIELDS = "UPDATE physician "
														    + "SET first_name = ?, last_name = ?, email = ?, phone = ?, specialty = ? "
														    + "WHERE id = ?";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to delete a physician from the database.
	private static final String DELETE_PHYSICIAN_BY_ID = "DELETE FROM physician "
													   + "WHERE id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) 
	{
		((ServletContext) externalContext.getContext()).log(msg);
	}

	//TODO Use the proper annotation here so that the correct data source object
	//     will be injected
	@Resource(name = "java:app/jdbc/databank")
	protected DataSource databankDS;
	protected Connection connectionToDataBase;
	protected PreparedStatement readAllPreparedStatement;
	protected PreparedStatement readByIdPreparedStatement;
	protected PreparedStatement createPreparedStatement;
	protected PreparedStatement updatePreparedStatement;
	protected PreparedStatement deleteByIdPreparedStatement;

	@PostConstruct
	protected void buildConnectionAndStatements() 
	{
		try 
		{
			logMsg("building connection and stmts");
			connectionToDataBase = databankDS.getConnection();
			readAllPreparedStatement = connectionToDataBase.prepareStatement(READ_ALL);
			createPreparedStatement = connectionToDataBase.prepareStatement(INSERT_PHYSICIAN, RETURN_GENERATED_KEYS);
			updatePreparedStatement = connectionToDataBase.prepareStatement(UPDATE_PHYSICIAN_ALL_FIELDS);
			deleteByIdPreparedStatement = connectionToDataBase.prepareStatement(DELETE_PHYSICIAN_BY_ID);
			//TODO Initialize other PreparedStatements here
		} 
		catch (Exception e) 
		{
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() 
	{
	    try 
	    {
	        logMsg("closing stmts and connection");
	        if (readAllPreparedStatement != null) readAllPreparedStatement.close();
	        if (createPreparedStatement != null) createPreparedStatement.close();
	        if (readByIdPreparedStatement != null) readByIdPreparedStatement.close();
	        if (updatePreparedStatement != null) updatePreparedStatement.close();
	        if (deleteByIdPreparedStatement != null) deleteByIdPreparedStatement.close();
	        if (connectionToDataBase != null) connectionToDataBase.close();
	    }
	    catch (SQLException e) 
	    {
	        logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
	    }
	}
	
	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		logMsg("reading all physicians");
		List<PhysicianPojo> physicians = new ArrayList<>();
		try (ResultSet resultSet = readAllPreparedStatement.executeQuery();) {

			while (resultSet.next()) {
				PhysicianPojo newPhysician = new PhysicianPojo();
				newPhysician.setId(resultSet.getInt("id"));
				newPhysician.setLastName(resultSet.getString("last_name"));
				newPhysician.setFirstName(resultSet.getString("first_name"));
				newPhysician.setEmail(resultSet.getString("email"));
				newPhysician.setPhoneNumber(resultSet.getString("phone"));
				newPhysician.setSpecialty(resultSet.getString("specialty"));
				//TODO Complete the physician initialization here
				newPhysician.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
				physicians.add(newPhysician);
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}
		
		return physicians;

	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) 
	{
		logMsg("creating a physician");
		//TODO Complete the insertion of a new physician here
		//TODO Be sure to use try-and-catch statement
		
		try 
		{
	        // Set values for the INSERT statement
	        createPreparedStatement.setString(1, physician.getLastName());
	        createPreparedStatement.setString(2, physician.getFirstName());
	        createPreparedStatement.setString(3, physician.getEmail());
	        createPreparedStatement.setString(4, physician.getPhoneNumber());
	        createPreparedStatement.setString(5, physician.getSpecialty());
	        createPreparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(physician.getCreated()));

	        // Execute the insert statement and retrieve the generated key (ID)
	        int affectedRows = createPreparedStatement.executeUpdate();
	        
	        if (affectedRows == 0) 
	        	throw new SQLException("Inserting physician failed, no rows affected.");
	        

	        // Retrieve the generated ID
	        try (ResultSet generatedKeys = createPreparedStatement.getGeneratedKeys()) 
	        {
	            if (generatedKeys.next()) 
	            	physician.setId(generatedKeys.getInt(1));
	            else	            
	                throw new SQLException("Inserting physician failed, no ID obtained.");
	            
	        }
	    } 
		catch (SQLException e) 
		{
	        logMsg("Error creating physician: " + e.getLocalizedMessage());
	        e.printStackTrace();
	        return null;  // Return null in case of failure
	    }
		
		return physician;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
	    logMsg("Reading a specific physician by ID");
	    PhysicianPojo physician = null;

	    try 
	    {
	        // Set the physician ID for the SELECT statement
	        readByIdPreparedStatement.setInt(1, physicianId);

	        // Execute the query
	        try (ResultSet resultSet = readByIdPreparedStatement.executeQuery()) 
	        {
	            if (resultSet.next()) 
	            {
	                physician = new PhysicianPojo();
	                physician.setId(resultSet.getInt("id"));
	                physician.setLastName(resultSet.getString("last_name"));
	                physician.setFirstName(resultSet.getString("first_name"));
	                physician.setEmail(resultSet.getString("email"));
	                physician.setPhoneNumber(resultSet.getString("phone"));
	                physician.setSpecialty(resultSet.getString("specialty"));
	                // Continue populating the physician object as needed
	            }
	        }
	    } 
	    catch (SQLException e) 
	    {
	        logMsg("Error reading physician: " + e.getLocalizedMessage());
	        e.printStackTrace();
	    }

	    return physician;  // Return the physician or null if not found
	}
	
	@Override
	public void updatePhysician(PhysicianPojo physician) 
	{
	    logMsg("Updating a specific physician");

	    try 
	    {
	        // Set the values for the UPDATE statement
	        updatePreparedStatement.setString(1, physician.getFirstName());
	        updatePreparedStatement.setString(2, physician.getLastName());
	        updatePreparedStatement.setString(3, physician.getEmail());
	        updatePreparedStatement.setString(4, physician.getPhoneNumber());
	        updatePreparedStatement.setString(5, physician.getSpecialty());
	        updatePreparedStatement.setInt(6, physician.getId());

	        // Execute the update statement
	        int affectedRows = updatePreparedStatement.executeUpdate();
	        
	        if (affectedRows == 0) 
	            throw new SQLException("Updating physician failed, no rows affected.");
	        
	    } 
	    catch (SQLException e) 
	    {
	        logMsg("Error updating physician: " + e.getLocalizedMessage());
	        e.printStackTrace();
	    }
	}

	@Override
	public void deletePhysicianById(int physicianId) 
	{
	    logMsg("Deleting a specific physician by ID");

	    try 
	    {
	        // Set the ID for the DELETE statement
	        deleteByIdPreparedStatement.setInt(1, physicianId);

	        // Execute the delete statement
	        int affectedRows = deleteByIdPreparedStatement.executeUpdate();
	        
	        if (affectedRows == 0) 
	            throw new SQLException("Deleting physician failed, no rows affected.");
	        
	    } 
	    catch (SQLException e) 
	    {
	        logMsg("Error deleting physician: " + e.getLocalizedMessage());
	        e.printStackTrace();
	    }
	}

}