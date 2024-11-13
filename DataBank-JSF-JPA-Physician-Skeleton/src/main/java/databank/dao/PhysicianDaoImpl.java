/********************************************************************************************************2*4*w*
 * File:  PhysicianDaoImpl.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.model.PhysicianPojo;


/**
 * Description:  Implements the C-R-U-D API for the database
 * 
 * TODO 01 - Some components are managed by CDI.<br>
 * TODO 02 - Methods which perform DML need @Transactional annotation.<br>
 * TODO 03 - Fix the syntax errors to correct methods. <br>
 * TODO 04 - Refactor this class.  Move all the method bodies and EntityManager to a new service class (e.g. StudentService) which is a
 * singleton (EJB).<br>
 * TODO 05 - Inject the service class using EJB.<br>
 * TODO 06 - Call all the methods of service class from each appropriate method here.
 */
@Named
@ApplicationScoped
public class PhysicianDaoImpl implements PhysicianDao, Serializable {

	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PhysicianPojo updatePhysician(PhysicianPojo physician) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		// TODO Auto-generated method stub
		
	}
	/** explicitly set serialVersionUID */
	/*private static final long serialVersionUID = 1L;

	//Get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	protected EntityManager em;

	@Override
	public List<PhysicianPojo> readAllPhysicians() {
		LOG.debug("reading all physicians");
		//Use the named JPQL query from the PhysicianPojo class to grab all the students
		TypedQuery<PhysicianPojo> allPhysiciansQuery = em.createNamedQuery(PhysicianPojo.PHYSICIAN_FIND_ALL, PhysicianPojo.class);
		//Execute the query and return the result/s.
		return allPhysiciansQuery.getResultList();
	}

	@Override
	public PhysicianPojo createPhysician(PhysicianPojo physician) {
		LOG.debug("creating a physician = {}", physician);
		em.something(physician);
		return physician;
	}

	@Override
	public PhysicianPojo readPhysicianById(int physicianId) {
		LOG.debug("read a specific physician = {}", physicianId);
		return em.something(PhysicianPojo.class, physicianId);
	}

	@Override
	public PhysicianPojo updatePhysician(PhysicianPojo physicianWithUpdates) {
		LOG.debug("updating a specific physician = {}", physicianWithUpdates);
		PhysicianPojo mergedPhysicianPojo = em.something(physicianWithUpdates);
		return mergedPhysicianPojo;
	}

	@Override
	public void deletePhysicianById(int physicianId) {
		LOG.debug("deleting a specific physicianID = {}", physicianId);
		PhysicianPojo physician = readPhysicianById(physicianId);
		LOG.debug("deleting a specific physician = {}", physician);
		if (physician != null) {
			em.refresh(physician);
			em.remove(physician);
		}
	}*/

} 
