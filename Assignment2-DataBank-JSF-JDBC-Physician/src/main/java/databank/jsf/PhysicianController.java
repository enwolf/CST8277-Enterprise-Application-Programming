/**
 * This file contains the PhysicianController class, which is responsible for managing 
 * physician-related data and operations in a JSF-based web application. It supports 
 * CRUD (Create, Read, Update, Delete) operations on physician records and handles 
 * navigation between different views in the application.
 * 
 * The file integrates with Jakarta EE technologies, including managed beans, dependency 
 * injection, and session-scoped components. It interacts with DAOs to perform data 
 * operations and facilitates user interactions with physician data through various 
 * forms and views.
 * 
 * @file PhysicianController.java
 * @version 1.0
 * @since 2024-10-04
 * @author Robin Phillis
 */
package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.SessionMap;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import databank.dao.ListDataDao;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;

/**
 * This class acts as a managed bean in a JSF web application for controlling 
 * physician-related data. It handles CRUD operations (Create, Read, Update, Delete) 
 * on physician records and manages the navigation logic between various views, 
 * such as forms for adding, editing, and deleting physicians.
 * 
 * The PhysicianController interacts with the PhysicianDao for database operations 
 * and ListDataDao for retrieving additional data, such as physician specialties. 
 * It uses a session map to store transient objects between views, ensuring 
 * that physician data persists while the user navigates the application.
 * 
 * This class is annotated with {@code @Named} and {@code @SessionScoped}, making it 
 * available as a JSF managed bean throughout the user's session. The controller 
 * is {@code Serializable} to maintain the bean's state between HTTP requests.
 */

@Named
@SessionScoped
public class PhysicianController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    @SessionMap
    private Map<String, Object> sessionMap;

    @Inject
    private PhysicianDao physicianDao;

    @Inject
    private ListDataDao listDataDao;

    private List<PhysicianPojo> physicians;

    /**
     * Loads the list of all physician records from the database by calling the {@link PhysicianDao#readAllPhysicians()} 
     * method. The loaded physicians are then stored in the local list of physicians managed by this controller.
     */
    public void loadPhysicians() 
    {
        setPhysicians(physicianDao.readAllPhysicians());
    }

    /**
     * Retrieves the list of all physicians currently managed by this controller. This list is populated 
     * by calling the {@link #loadPhysicians()} method.
     * 
     * @return a list of {@link PhysicianPojo} representing all physicians.
     */
    public List<PhysicianPojo> getPhysicians() 
    {
        return physicians;
    }

    /**
     * Sets the list of physicians to be managed by this controller.
     * 
     * @param physicians a list of {@link PhysicianPojo} representing the physicians to be managed.
     */
    public void setPhysicians(List<PhysicianPojo> physicians) 
    {
        this.physicians = physicians;
    }

    /**
     * Retrieves the list of all available specialties from the database by calling the {@link ListDataDao#readAllSpecialties()} 
     * method. This method is used to populate specialty dropdowns in the JSF forms.
     * 
     * @return a list of strings representing the available specialties for physicians.
     */
    public List<String> getSpecialties() 
    {
        return this.listDataDao.readAllSpecialties();
    }
    
    /**
     * Prepares a new physician object and stores it in the session map, allowing the user to navigate 
     * to the "add physician" form for creating a new physician record. The session map temporarily holds 
     * the physician data until it is submitted.
     * 
     * @return a string representing the navigation outcome, which redirects the user to the "add-physician.xhtml" page.
     */
    public String navigateToAddForm() 
    {
        PhysicianPojo newPhysician = new PhysicianPojo();
        sessionMap.put("newPhysician", newPhysician);
        return "/add-physician.xhtml?faces-redirect=true";
    }

    /**
     * Submits a new physician to be created in the database. The physician's creation date is automatically 
     * set to the current time, and the {@link PhysicianDao#createPhysician(PhysicianPojo)} method is called 
     * to persist the physician data. In case of an exception, the error is logged, and the method returns null.
     * 
     * @param physician the physician object to be created in the database.
     * @return a string representing the navigation outcome, which redirects the user to the "list-physicians.xhtml" page.
     *         Returns null if an exception occurs during the creation process.
     */
    public String submitPhysician(PhysicianPojo physician) 
    {
        physician.setCreated(java.time.LocalDateTime.now());
        
        try 
        {
            physicianDao.createPhysician(physician);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
            return null;
        }

        return "list-physicians.xhtml?faces-redirect=true"; 
    }

    /**
     * Loads the existing physician data for the specified physician ID and stores it in the session map,
     * allowing the user to navigate to the "edit physician" form for updating the physician's details.
     * 
     * @param physicianId the unique identifier of the physician to be updated.
     * @return a string representing the navigation outcome, which redirects the user to the "edit-physician.xhtml" page.
     */
    public String navigateToUpdateForm(int physicianId) 
    {
        PhysicianPojo physicianToUpdate = physicianDao.readPhysicianById(physicianId);
        sessionMap.put("editPhysician", physicianToUpdate);
        return "/edit-physician.xhtml?faces-redirect=true";
    }

    /**
     * Submits the updated physician data to the database by calling the {@link PhysicianDao#updatePhysician(PhysicianPojo)} 
     * method. Once updated, the user is redirected to the "list-physicians.xhtml" page to view the list of physicians.
     * 
     * @param physician the physician object containing the updated data.
     * @return a string representing the navigation outcome, which redirects the user to the "list-physicians.xhtml" page.
     */
    public String submitUpdatedPhysician(PhysicianPojo physician) 
    {
        physicianDao.updatePhysician(physician);
        return "list-physicians.xhtml?faces-redirect=true";
    }

    /**
     * Deletes the physician record for the specified physician ID from the database by calling the 
     * {@link PhysicianDao#deletePhysicianById(int)} method. After deletion, the user is redirected to 
     * the "list-physicians.xhtml" page to view the updated list of physicians.
     * 
     * @param physicianId the unique identifier of the physician to be deleted.
     * @return a string representing the navigation outcome, which redirects the user to the "list-physicians.xhtml" page.
     */
    public String deletePhysician(int physicianId) 
    {
        physicianDao.deletePhysicianById(physicianId);
        return "list-physicians.xhtml?faces-redirect=true";
    }
}
