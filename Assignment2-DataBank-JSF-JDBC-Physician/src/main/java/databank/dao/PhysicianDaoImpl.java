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
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;
import javax.sql.DataSource;

import databank.model.PhysicianPojo;

@SuppressWarnings("unused")
/**
 * Description:  Implements the C-R-U-D API for the database
 */
//TODO Don't forget this is a managed bean with an application scope
public class PhysicianDaoImpl implements PhysicianDao, Serializable {
	/** Explicitly set serialVersionUID */
	private static final long serialVersionUID = 1L;

	//TODO Set the value of this string constant properly.  This is the JNDI name
	//     for the data source.
	private static final String DATABANK_DS_JNDI = "java:comp/env/jdbc/H2Pool"; //I hope this is correct?
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to retrieve the list of physicians from the database.
	private static final String READ_ALL = "SELECT id, last_name, first_name, email, phone, specialty "
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
	private static final String UPDATE_PHYSICIAN_ALL_FIELDS = "UPDATE physician (first_name, last_name, email, phone_number, specialty) "
															+ "VALUES (?, ?, ?, ?, ?)";
	//TODO Set the value of this string constant properly.  This is the SQL
	//     statement to delete a physician from the database.
	private static final String DELETE_PHYSICIAN_BY_ID = "DELETE FROM physicians "
													   + "WHERE id = ?";

	@Inject
	protected ExternalContext externalContext;

	private void logMsg(String msg) {
		((ServletContext) externalContext.getContext()).log(msg);
	}

	//TODO Use the proper annotation here so that the correct data source object
	//     will be injected
	@Resource(name = "jdbc/H2Pool")
	protected DataSource databankDS;

	protected Connection connectionToDataBase;
	protected PreparedStatement readAllPreparedStatement;
	protected PreparedStatement readByIdPreparedStatement;
	protected PreparedStatement createPreparedStatement;
	protected PreparedStatement updatePreparedStatement;
	protected PreparedStatement deleteByIdPreparedStatement;

	@PostConstruct
	protected void buildConnectionAndStatements() {
		try {
			logMsg("building connection and stmts");
			connectionToDataBase = databankDS.getConnection();
			readAllPreparedStatement = connectionToDataBase.prepareStatement(READ_ALL);
			createPreparedStatement = connectionToDataBase.prepareStatement(INSERT_PHYSICIAN, RETURN_GENERATED_KEYS);
			updatePreparedStatement = connectionToDataBase.prepareStatement(UPDATE_PHYSICIAN_ALL_FIELDS);
			deleteByIdPreparedStatement = connectionToDataBase.prepareStatement(DELETE_PHYSICIAN_BY_ID);
			//TODO Initialize other PreparedStatements here
		} catch (Exception e) {
			logMsg("something went wrong getting connection from database:  " + e.getLocalizedMessage());
		}
	}

	@PreDestroy
	protected void closeConnectionAndStatements() {
		try {
			logMsg("closing stmts and connection");
			readAllPreparedStatement.close();
			createPreparedStatement.close();
			//TODO Close other PreparedStatements here
			connectionToDataBase.close();
		} catch (Exception e) {
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
				physicians.add(newPhysician);
			}
			
		} catch (SQLException e) {
			logMsg("something went wrong accessing database:  " + e.getLocalizedMessage());
		}
		
		return physicians;

	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		logMsg("creating a physician");
		//TODO Complete the insertion of a new physician here
		//TODO Be sure to use try-and-catch statement
		return null;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		logMsg("read a specific physician");
		//TODO Complete the retrieval of a specific physician by its id here
		//TODO Be sure to use try-and-catch statement
		return null;
	}

	@Override
	public void updatePhysician(PhysicianPojo physician) {
		logMsg("updating a specific physician");
		//TODO Complete the update of a specific physician here
		//TODO Be sure to use try-and-catch statement
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		logMsg("deleting a specific physician");
		//TODO Complete the deletion of a specific physician here
		//TODO Be sure to use try-and-catch statement
	}

}