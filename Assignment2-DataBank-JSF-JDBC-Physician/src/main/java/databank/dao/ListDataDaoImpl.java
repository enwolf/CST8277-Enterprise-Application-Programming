/*********************************************************************************************************
 * File:  ListDataDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
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
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@SuppressWarnings("unused")
/**
 * Description:  API for reading list data from the database
 */
//TODO Annotate this class so that it becomes a managed bean
//TODO Provide the proper scope for this managed bean
@Named
@RequestScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//TODO Set the value of this string constant properly.  This is the JNDI name
	//     for the data source.
	private static final String DATABANK_DS_JNDI = "java:app/jdbc/databank";;
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve the list of specialties from the database.
	private static final String READ_ALL_SPECIALTIES = "SELECT name FROM specialty";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	//TODO Use the proper annotation here so that the correct data source object
	//     will be injected
	@Resource(name = "java:app/jdbc/databank")
	protected DataSource databankDS;
	protected Connection connectionToDatabase;
	protected PreparedStatement readAllSpecialtiesPreparedStatement;

	@PostConstruct
	protected void buildConnectionAndStatements() {
	    try {
	        logMsg("Attempting to get connection from data source.");

	        // Establish the connection
	        connectionToDatabase = databankDS.getConnection();
	        logMsg("Connection established successfully.");

	        // Initialize the PreparedStatement
	        readAllSpecialtiesPreparedStatement = connectionToDatabase.prepareStatement(READ_ALL_SPECIALTIES);
	        logMsg("PreparedStatement initialized successfully.");

	    } catch (Exception e) {
	        logMsg("Something went wrong getting connection from database: " + e.getLocalizedMessage());
	        e.printStackTrace();  // Ensure stack trace shows the root cause
	    }
	}
	
	

	@PreDestroy
	protected void closeConnectionAndStatements() 
	{
		try 
		{
			logMsg("closing stmts and connection");
			
			if (readAllSpecialtiesPreparedStatement != null) readAllSpecialtiesPreparedStatement.close();
			connectionToDatabase.close();
		} 
		catch (Exception e) 
		{
			logMsg("something went wrong closing stmts or connection:  " + e.getLocalizedMessage());
		}
	}

	@Override
	public List<String> readAllSpecialties() {
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
