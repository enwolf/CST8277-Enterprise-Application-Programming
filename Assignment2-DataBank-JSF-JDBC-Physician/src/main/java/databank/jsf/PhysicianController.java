/*********************************************************************************************************
 * File:  PhysicianController.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.SessionMap;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import databank.dao.ListDataDao;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;

/**
 * Description:  Responsible for collection of Physician Pojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 */

@Named
@SessionScoped
public class PhysicianController implements Serializable {
	private static final long serialVersionUID = 1L;

	//TODO Use the proper annotations here so that this session map object will be 
	//     injected.  Please refer to Week 3 sample project to know how this is to be done. 
    @Inject
    @SessionMap
	private Map<String, Object> sessionMap;

	@Inject
	private PhysicianDao physicianDao;

	@Inject
	private ListDataDao listDataDao;

	private List<PhysicianPojo> physicians;
	
    

	//Necessary methods to make controller work

	public void loadPhysicians() {
		setPhysicians(physicianDao.readAllPhysicians());
	}

	public List<PhysicianPojo> getPhysicians() {
		return physicians;
	}

	public void setPhysicians(List<PhysicianPojo> physicians) {
		this.physicians = physicians;
	}

	public List<String> getSpecialties() {
		return this.listDataDao.readAllSpecialties();
	}
	
	public String navigateToAddForm() {
		//Pay attention to the name here, it will be used as the object name in add-physician.xhtml
		//ex. <h:inputText value="#{newPhysician.firstName}" id="firstName" />
		
		
	    // Create a new PhysicianPojo for the add form
	    PhysicianPojo newPhysician = new PhysicianPojo();
	    
	    // Store this new object in the session map for the form to bind to
	    sessionMap.put("newPhysician", newPhysician);
	    
	    // Navigate to the add-physician.xhtml page
	    return "/add-physician.xhtml?faces-redirect=true";
	}

	public String submitPhysician(PhysicianPojo physician) {
		//TODO Update the physician object with current date here.  You can use LocalDateTime::now().
		//TODO Use DAO to insert the physician to the database
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
		  // Update the physician object with the current date and time
	    physician.setCreated(java.time.LocalDateTime.now());
	    
	    // Use the DAO to insert the physician into the database
	    try {
	        physicianDao.createPhysician(physician);
	        // Log a message indicating the physician was created successfully
	        
	    } catch (Exception e) {
	        // Log an error message if something goes wrong during the creation process
	        
	        e.printStackTrace();
	        // Return null or handle the error as appropriate for your application
	        return null;
	    }
	    
	    // Redirect the user back to the list of physicians page after successful submission
	    return "list-physicians.xhtml?faces-redirect=true";

	    
	}

	public String navigateToUpdateForm(int physicianId) {
		//TODO Use DAO to find the physician object from the database first
		//TODO Use session map to keep track of of the object being edited
		//TODO Do not forget to navigate the user to the edit/update form
	    // Use DAO to find the physician by ID
	    PhysicianPojo physicianToUpdate = physicianDao.readPhysicianById(physicianId);
	    
	    // Store the physician object in the session map for editing
	    sessionMap.put("editPhysician", physicianToUpdate);
	    
	    // Navigate to the update form
	    return "/edit-physician.xhtml?faces-redirect=true";
	}

	public String submitUpdatedPhysician(PhysicianPojo physician) {
		//TODO Use DAO to update the physician in the database
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
	    
		// Use DAO to update the physician in the database
	    physicianDao.updatePhysician(physician);
	    
	    // Redirect the user back to the list of physicians
	    return "list-physicians.xhtml?faces-redirect=true";
	}

	public String deletePhysician(int physicianId) {
		//TODO Use DAO to delete the physician from the database
		//TODO Do not forget to navigate the user back to list-physicians.xhtml
		
		// Use DAO to delete the physician from the database
	    physicianDao.deletePhysicianById(physicianId);
	    
	    // Redirect the user back to the list of physicians
	    return "list-physicians.xhtml?faces-redirect=true";
	}
	
	

}
