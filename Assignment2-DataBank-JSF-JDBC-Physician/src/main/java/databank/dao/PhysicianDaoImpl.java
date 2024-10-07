/**
 * This file contains the implementation of the PhysicianDao interface, responsible for
 * managing CRUD operations for physician records in the database. It handles all
 * database-related actions such as creating, reading, updating, and deleting physician data.
 * The file makes use of Jakarta EE annotations for dependency injection, request-scoping,
 * and resource management.
 * 
 * The lifecycle methods ensure that database resources are properly initialized and cleaned up,
 * preventing resource leaks. The class also logs critical actions using the servlet context's
 * logging mechanism.
 * 
 * @file PhysicianDaoImpl.java
 * @version 1.0
 * @since 2024-10-04
 * @author Robin Phillis
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
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;
import databank.model.PhysicianPojo;

/**
 * Implementation of the {@link PhysicianDao} interface, responsible for performing
 * CRUD operations (Create, Read, Update, Delete) on physician records in the database.
 * This DAO uses JDBC for database interaction and manages connections and prepared
 * statements for each operation.
 * 
 * The class is scoped to individual HTTP requests using {@code @RequestScoped}, ensuring
 * a new instance of the DAO is created for each incoming request. It also makes use of
 * dependency injection to access the database through a {@link DataSource}, as well as
 * an {@link ExternalContext} for logging purposes.
 * 
 * Lifecycle methods annotated with {@code @PostConstruct} and {@code @PreDestroy} handle
 * the initialization and cleanup of the database connection and prepared statements, ensuring
 * that resources are properly managed during the bean's lifecycle.
 * 
 * Logging is performed using the {@link ServletContext}'s log mechanism.
 * 
 */

@SuppressWarnings("unused")
@Named
@RequestScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {

	private static final long serialVersionUID = 1L;

	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank"; 

	private static final String READ_ALL = "SELECT id, last_name, first_name, email, phone, specialty, created "
							             + "FROM physician";
		
	private static final String READ_PHYSICIAN_BY_ID = "SELECT id, last_name, first_name, email, phone, specialty  "
													 + "FROM physician "
													 + "WHERE ID = ?";
	
	private static final String INSERT_PHYSICIAN = "INSERT INTO physician ( last_name, first_name, email, phone, specialty, created) "
												 + "VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final String UPDATE_PHYSICIAN_ALL_FIELDS = "UPDATE physician "
														    + "SET first_name = ?, last_name = ?, email = ?, phone = ?, specialty = ? "
														    + "WHERE id = ?";
	
	private static final String DELETE_PHYSICIAN_BY_ID = "DELETE FROM physician "
													   + "WHERE id = ?";

	@Inject
	protected ExternalContext externalContext;

	@Resource(name = "java:app/jdbc/databank")
	protected DataSource databankDS;
	protected Connection connectionToDataBase;
	protected PreparedStatement readAllPreparedStatement;
	protected PreparedStatement readByIdPreparedStatement;
	protected PreparedStatement createPreparedStatement;
	protected PreparedStatement updatePreparedStatement;
	protected PreparedStatement deleteByIdPreparedStatement;

	/**
	 * Logs a message to the server log using the servlet context.
	 * 
	 * @param msg the message to log.
	 */
	private void logMsg(String msg) 
	{
		((ServletContext) externalContext.getContext()).log(msg);
	}

	/**
	 * Initializes the connection to the database and prepares the SQL statements 
	 * for CRUD operations. This method is called after the bean's construction 
	 * and is annotated with {@code @PostConstruct}.
	 */
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
	        readByIdPreparedStatement = connectionToDataBase.prepareStatement(READ_PHYSICIAN_BY_ID);

	    }
	    catch (Exception e) 
	    {
	        logMsg("something went wrong getting connection from database: " + e.getLocalizedMessage());
	    }
	}

	/**
	 * Closes the database connection and prepared statements. This method is called before 
	 * the bean is destroyed and is annotated with {@code @PreDestroy}.
	 */
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
	
	/**
	 * Reads all physician records from the database.
	 * 
	 * @return a list of {@link PhysicianPojo} objects representing all physicians in the database.
	 */
	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		logMsg("reading all physicians");
		List<PhysicianPojo> physicians = new ArrayList<>();
		try (ResultSet resultSet = readAllPreparedStatement.executeQuery();) 
		{
			while (resultSet.next()) 
			{
				PhysicianPojo newPhysician = new PhysicianPojo();
				newPhysician.setId(resultSet.getInt("id"));
				newPhysician.setLastName(resultSet.getString("last_name"));
				newPhysician.setFirstName(resultSet.getString("first_name"));
				newPhysician.setEmail(resultSet.getString("email"));
				newPhysician.setPhoneNumber(resultSet.getString("phone"));
				newPhysician.setSpecialty(resultSet.getString("specialty"));
				newPhysician.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
				physicians.add(newPhysician);
			}
		} 
		catch (SQLException e) 
		{
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}
		
		return physicians;
	}

	/**
	 * Inserts a new physician record into the database and sets the generated ID in the 
	 * {@link PhysicianPojo} object. The method uses a prepared statement and handles 
	 * potential SQL exceptions.
	 * 
	 * @param physician the physician object to be created in the database.
	 * @return the created physician with the generated ID, or {@code null} if the creation failed.
	 */
	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) 
	{
		logMsg("creating a physician");
				
		try 
		{
	        createPreparedStatement.setString(1, physician.getLastName());
	        createPreparedStatement.setString(2, physician.getFirstName());
	        createPreparedStatement.setString(3, physician.getEmail());
	        createPreparedStatement.setString(4, physician.getPhoneNumber());
	        createPreparedStatement.setString(5, physician.getSpecialty());
	        createPreparedStatement.setTimestamp(6, java.sql.Timestamp.valueOf(physician.getCreated()));

	        int affectedRows = createPreparedStatement.executeUpdate();
	        
	        if (affectedRows == 0) 
	        	throw new SQLException("Inserting physician failed, no rows affected.");
	    
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
	        return null;  
	    }		
		return physician;
	}

	/**
	 * Reads a physician record from the database by its ID.
	 * 
	 * @param physicianId the ID of the physician to be read.
	 * @return the physician object corresponding to the given ID, or {@code null} if not found.
	 */
	@Override
	public PhysicianPojo readPhysicianById(int physicianId) 
	{
	    logMsg("Reading a specific physician by ID");
	    PhysicianPojo physician = null;

	    try 
	    {
	        readByIdPreparedStatement.setInt(1, physicianId);

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
	            }
	        }
	    } 
	    catch (SQLException e) 
	    {
	        logMsg("Error reading physician: " + e.getLocalizedMessage());
	        e.printStackTrace();
	    }

	    return physician;
	}
	
	/**
	 * Updates the physician record in the database with new data. The physician object 
	 * contains the updated information, and the method uses a prepared statement to 
	 * execute the update.
	 * 
	 * @param physician the physician object containing updated data.
	 */
	@Override
	public void updatePhysician(PhysicianPojo physician) 
	{
	    logMsg("Updating a specific physician");

	    try 
	    {
	        updatePreparedStatement.setString(1, physician.getFirstName());
	        updatePreparedStatement.setString(2, physician.getLastName());
	        updatePreparedStatement.setString(3, physician.getEmail());
	        updatePreparedStatement.setString(4, physician.getPhoneNumber());
	        updatePreparedStatement.setString(5, physician.getSpecialty());
	        updatePreparedStatement.setInt(6, physician.getId());

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

	/**
	 * Deletes a physician record from the database by its ID. The method uses a prepared 
	 * statement and handles potential SQL exceptions.
	 * 
	 * @param physicianId the ID of the physician to be deleted.
	 */
	@Override
	public void deletePhysicianById(int physicianId) 
	{
	    logMsg("Deleting a specific physician by ID");

	    try 
	    {
	        deleteByIdPreparedStatement.setInt(1, physicianId);

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
