
package databank.jsf;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import databank.service.PhysicianService;
import databank.dao.ListDataDao;
import databank.model.PhysicianPojo;

/**
 * Controller class for managing a collection of {@link PhysicianPojo} objects in the JSF front-end.
 * This class handles C-R-U-D operations through interaction with the service layer.
 * @Author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@Named("physicianController")
@SessionScoped
public class PhysicianController implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Logger instance for logging events in the controller. */
    private static final Logger LOG = LogManager.getLogger();

    /** Expression for accessing the UI constants resource bundle. */
    public static final String UICONSTS_BUNDLE_EXPR = "#{uiconsts}";
    /** Message key for missing refresh error. */
    public static final String PHYSICIAN_MISSING_REFRESH_BUNDLE_MSG = "refresh";
    /** Message key for out-of-date refresh error. */
    public static final String PHYSICIAN_OUTOFDATE_REFRESH_BUNDLE_MSG = "outOfDate";

    /** Injected FacesContext for managing JSF context operations. */
    @Inject
    protected FacesContext facesContext;

    /** Service layer for handling physician data operations. */
    @Inject
    protected PhysicianService physicianService;

    /** DAO for managing list data operations. */
    @Inject
    protected ListDataDao listDataDao;

    /** Injected resource bundle for UI constants. */
    @Inject
    @ManagedProperty(UICONSTS_BUNDLE_EXPR)
    protected ResourceBundle uiconsts;

    /** List of physicians managed by this controller. */
    protected List<PhysicianPojo> physician;

    /** Boolean flag indicating if the add physician form is rendered. */
    protected boolean adding;

    /**
     * Loads the list of all physicians.
     */
    public void loadPhysicians() 
    {
        LOG.debug("loadPhysicians");
        try {
            physician = physicianService.readAllPhysicians();
        } catch (Exception e) {
            LOG.error("Error loading physicians: ", e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading data", null));
        }
    }

    /**
     * Gets the list of physicians.
     *
     * @return the list of physicians.
     */
    public List<PhysicianPojo> getPhysicians() 
    {
        return this.physician;
    }

    /**
     * Sets the list of physicians.
     *
     * @param physicians the new list of physicians.
     */
    public void setPhysicians(List<PhysicianPojo> physicians) 
    {
        this.physician = physicians;
    }

    /**
     * Checks if the add physician form is being displayed.
     *
     * @return true if the form is displayed, false otherwise.
     */
    public boolean isAdding() 
    {
        return adding;
    }

    /**
     * Sets the adding state for the add physician form.
     *
     * @param adding true to show the form, false to hide it.
     */
    public void setAdding(boolean adding) 
    {
        this.adding = adding;
    }

    /**
     * Toggles the visibility of the add physician form.
     */
    public void toggleAdding() 
    {
        this.adding = !this.adding;
    }

    /**
     * Marks a physician record as editable.
     *
     * @param physician the physician to be edited.
     * @return null to stay on the current page.
     */
    public String editPhysician(PhysicianPojo physician)
    {
        LOG.debug("editPhysician = {}", physician);
        physician.setEditable(true);
        return null;
    }

    /**
     * Updates a physician record with new data.
     *
     * @param physicianWithEdits the updated physician data.
     * @return null to stay on the current page.
     */
    public String updatePhysician(PhysicianPojo physicianWithEdits) 
    {
        LOG.debug("updatePhysician = {}", physicianWithEdits);
        
        PhysicianPojo physicianToBeUpdated = physicianService.readPhysicianById(physicianWithEdits.getId());
    
        if (physicianToBeUpdated == null) 
        {
            LOG.debug("FAILED update physician, does not exist = {}", physicianToBeUpdated);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    uiconsts.getString(PHYSICIAN_MISSING_REFRESH_BUNDLE_MSG), null));
        }
        else 
        {
            physicianToBeUpdated = physicianService.updatePhysician(physicianWithEdits);
        
            if (physicianToBeUpdated == null) 
            {
                LOG.debug("FAILED update physician = {}", physicianToBeUpdated);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        uiconsts.getString(PHYSICIAN_OUTOFDATE_REFRESH_BUNDLE_MSG), null));
            }
            else 
            {
                physicianToBeUpdated.setEditable(false);
                int idx = physician.indexOf(physicianWithEdits);
                physician.remove(idx);
                physician.add(idx, physicianToBeUpdated);
            }
        }
        return null;
    }

    /**
     * Cancels the edit operation for a physician record.
     *
     * @param physician the physician whose edit is to be cancelled.
     * @return null to stay on the current page.
     */
    public String cancelUpdate(PhysicianPojo physician) 
    {
        LOG.debug("cancelUpdate = {}", physician);
        physician.setEditable(false);
        return null;
    }

    /**
     * Deletes a physician record by ID.
     *
     * @param physicianId the ID of the physician to be deleted.
     */
    public void deletePhysician(int physicianId) 
    {
        LOG.debug("deletePhysician = {}", physicianId);
        PhysicianPojo physicianToBeRemoved = physicianService.readPhysicianById(physicianId);
    
        if (physicianToBeRemoved == null) 
        {
            LOG.debug("failed deletePhysician does not exist = {}", physicianId);
            return;
        }
        
        physicianService.deletePhysicianById(physicianId);
        physician.remove(physicianToBeRemoved);
    }

    /**
     * Adds a new physician to the list.
     *
     * @param physician the new physician to be added.
     */
    public void addNewPhysician(PhysicianPojo physicians) 
    {
        LOG.debug("Attempting to add new physician: {}", physicians);
        try {
            PhysicianPojo newPhysician = physicianService.createPhysician(physicians);
            if (newPhysician != null) {
                LOG.debug("Successfully added new physician: {}", newPhysician);
                physician.add(newPhysician);
            } else {
                LOG.error("Failed to add new physician: {}", physicians);
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to add physician", null));
            }
        } catch (Exception e) {
            LOG.error("Error occurred while adding new physician: {}", e.getMessage(), e);
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding physician", null));
        }
    }

    /**
     * Refreshes the physician form by clearing all messages and redirecting to the index page.
     *
     * @return the navigation string for redirecting to the index page.
     */
    public String refreshPhysicianForm() 
    {
        LOG.debug("refreshPhysicianForm");
        Iterator<FacesMessage> facesMessageIterator = facesContext.getMessages();
        while (facesMessageIterator.hasNext()) 
        {
            facesMessageIterator.remove();
        }
        return "index.xhtml?faces-redirect=true";
    }

    /**
     * Retrieves a list of all specialties.
     *
     * @return a list of specialty strings.
     */
    public List<String> getSpecialties() 
    {
        return listDataDao.readAllSpecialties();
    }
}