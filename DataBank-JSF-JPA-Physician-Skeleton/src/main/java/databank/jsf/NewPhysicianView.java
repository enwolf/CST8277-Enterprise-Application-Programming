/********************************************************************************************************2*4*w*
 * File:  NewPhysicianView.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;

import databank.model.PhysicianPojo;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.inject.Inject;


/**
 * This class represents the scope of adding a new physician to the DB.
 * 
 * TODO 09 - This class is a managed bean.  Use the name "newPhysician".<br>
 * TODO 10 - Like previous assignment where PhysicianPojo was view scoped, this class is?<br>
 * TODO 11 - Add the missing variables and their getters and setters.  Have in mind dates and version are internal
 * only.<br>
 * 
 */
public class NewPhysicianView implements Serializable {
	/** explicit set serialVersionUID */
	private static final long serialVersionUID = 1L;

	protected String lastName;
	protected String firstName;

	@Inject
	@ManagedProperty("#{physicianController}")
	protected PhysicianController physicianController;

	public NewPhysicianView() {
	}

	/**
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void addPhysician() {
		if (allNotNullOrEmpty(firstName, lastName /* TODO 11 - Don't forget to add the other variables that are not nullable */)) {
			PhysicianPojo theNewPhysician = new PhysicianPojo();
			theNewPhysician.setFirstName(getFirstName());
			theNewPhysician.setLastName(getLastName());
			//TODO 12 - Call other setters
			physicianController.addNewPhysician(theNewPhysician);
			//Clean up
			physicianController.toggleAdding();
			setFirstName(null);
			setLastName(null);
			//TODO 13 - Set everything else to null
		}
	}

	static boolean allNotNullOrEmpty(final Object... values) {
		if (values == null) {
			return false;
		}
		for (final Object val : values) {
			if (val == null) {
				return false;
			}
			if (val instanceof String) {
				String str = (String) val;
				if (str.isEmpty()) {
					return false;
				}
			}
		}
		return true;
	}
}
