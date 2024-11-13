package databank.service;

import java.io.Serializable;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

/**
 * Service class implementing the PhysicianDao interface to provide 
 * CRUD operations for the Physician database.
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@Singleton
public class PhysicianService implements Serializable {
    /** Explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * Retrieves all physicians from the database.
     *
     * @return a list of PhysicianPojo objects representing all physicians.
     */
    
    public List<PhysicianPojo> readAllPhysicians() 
    {
        LOG.debug("Attempting to read all physicians from the database");
        try {
            TypedQuery<PhysicianPojo> allPhysiciansQuery = entityManager.createNamedQuery(PhysicianPojo.PHYSICIAN_FIND_ALL, PhysicianPojo.class);
            List<PhysicianPojo> results = allPhysiciansQuery.getResultList();
            LOG.debug("Fetched " + results.size() + " physicians from the database");
            return results;
        } catch (Exception e) {
            LOG.error("Error reading all physicians: ", e);
            throw e;
        }
    }

    /**
     * Retrieves a specific physician by their ID.
     *
     * @param physicianId the ID of the physician to be retrieved.
     * @return a PhysicianPojo object representing the physician, or null if not found.
     */
    
    public PhysicianPojo readPhysicianById(int physicianId) 
    {
        LOG.debug("read a specific physician = {}", physicianId);
        return entityManager.find(PhysicianPojo.class, physicianId);
    }

    /**
     * Creates a new physician record in the database.
     *
     * @param physician the PhysicianPojo object to be created.
     * @return the created PhysicianPojo object.
     */
    
    @Transactional
    public PhysicianPojo createPhysician(PhysicianPojo physician) 
    {
        LOG.debug("creating a physician = {}", physician);
        entityManager.persist(physician);
        return physician;
    }

    /**
     * Updates an existing physician record in the database.
     *
     * @param physicianWithUpdates the PhysicianPojo object containing the updated data.
     * @return the updated PhysicianPojo object.
     */
    
    @Transactional
    public PhysicianPojo updatePhysician(PhysicianPojo physicianWithUpdates) 
    {
        LOG.debug("updating a specific physician = {}", physicianWithUpdates);
        return entityManager.merge(physicianWithUpdates);
    }

    /**
     * Deletes a physician record from the database by their ID.
     *
     * @param physicianId the ID of the physician to be deleted.
     */
    
    @Transactional
    public void deletePhysicianById(int physicianId) 
    {
        LOG.debug("deleting a specific physicianID = {}", physicianId);
        PhysicianPojo physician = readPhysicianById(physicianId);
    
        if (physician != null) 
        {
        	entityManager.refresh(physician);
        	entityManager.remove(physician);
		}
    }
}
