/*********************************************************************************************************
 * File:  Physician.java Course Materials CST8277
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 2024-09-14
 * 
 * @description A simple POJO (Plain Old Java Object) representing a physician. This class 
 *              implements `Serializable` to allow for object serialization. It includes attributes 
 *              such as `id`, `lastName`, `firstName`, `email`, `phoneNumber`, `specialty`, and `created` 
 *              with corresponding getter and setter methods. It also overrides the `hashCode`, `equals`, 
 *              and `toString` methods.
 */
package jdbccmd;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A simple POJO for representing a physician. This class includes fields for various attributes of a 
 * physician and provides getter and setter methods for these fields. It implements the `Serializable` 
 * interface, which allows instances of this class to be serialized.
 * <p>
 * The class also overrides the `hashCode`, `equals`, and `toString` methods to provide custom behavior 
 * for these operations.
 * </p>
 * 
 * @see java.io.Serializable
 * @see java.time.LocalDateTime
 */
public class Physician implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** Unique identifier for the physician */
	protected int id;
	/** Last name of the physician */
	protected String lastName;
	/** First name of the physician */
	protected String firstName;
	/** Email address of the physician */
	protected String email;
	/** Phone number of the physician */
	protected String phoneNumber;
	/** Specialty of the physician */
	protected String specialty;
	/** Date and time when the physician record was created */
	protected LocalDateTime created;

	/**
	 * Gets the unique identifier for the physician.
	 * 
	 * @return the ID of the physician.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Gets the first name of the physician.
	 * 
	 * @return the first name of the physician.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the last name of the physician.
	 * 
	 * @return the last name of the physician.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the first name of the physician.
	 * 
	 * @param firstName the new first name of the physician.
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the email address of the physician.
	 * 
	 * @return the email address of the physician.
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Gets the phone number of the physician.
	 * 
	 * @return the phone number of the physician.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	/**
	 * Gets the specialty of the physician.
	 * 
	 * @return the specialty of the physician.
	 */
	public String getSpecialty() {
		return specialty;
	}
	
	/**
	 * Gets the date and time when the physician record was created.
	 * 
	 * @return the creation date and time of the physician record.
	 */
	public LocalDateTime getCreated() {
		return created;
	}
	
	/**
	 * Sets the unique identifier for the physician.
	 * 
	 * @param id the new ID of the physician.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the last name of the physician.
	 * 
	 * @param lastName the new last name of the physician.
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the email address of the physician.
	 * 
	 * @param email the new email address of the physician.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the phone number of the physician.
	 * 
	 * @param phoneNumber the new phone number of the physician.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the specialty of the physician.
	 * 
	 * @param specialty the new specialty of the physician.
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/**
	 * Sets the date and time when the physician record was created.
	 * 
	 * @param created the new creation date and time of the physician record.
	 */
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true;
		
		if (!(obj instanceof Physician)) 
			return false;
		
		Physician other = (Physician) obj;
	
		return id != other.id;
	}

    /**
     * Returns a string representation of the `Physician` object.
     * <p>
     * The returned string includes the values of all non-null fields of the `Physician` object, 
     * formatted as a human-readable string. Each field is represented in the form of `fieldName = value`, 
     * and fields are separated by commas. The string representation is useful for debugging and logging purposes.
     * </p>
     * 
     * @return a string representation of the `Physician` object, including all non-null fields.
     */
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("Physician[id = ").append(id);
		
		if (lastName != null) 
			builder.append(", lastName = ").append(lastName);
		
		if (firstName != null) 
			builder.append(", firstName = ").append(firstName);
		
		if (email != null) 
			builder.append(", email = ").append(email);
		
		if (phoneNumber != null) 
			builder.append(", phoneNumber = ").append(phoneNumber);
		
		if (specialty != null) 
			builder.append(", specialty = ").append(specialty);

		if (created != null) 
			builder.append(", created = ").append(created);
		
		return builder.append("]").toString();
	}
	
}//END of Class Physician
