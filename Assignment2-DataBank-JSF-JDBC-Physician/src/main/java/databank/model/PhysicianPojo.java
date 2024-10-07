/**
 * This file contains the model class PhysicianPojo, which represents a physician entity
 * in the application. The class holds key details such as the physician's name, contact 
 * information, specialty, and the date the record was created. It is used in conjunction 
 * with the PhysicianController and PhysicianDao for handling CRUD operations and 
 * representing the physician's data in various views.
 * 
 * The class is serializable to support the transfer of data across different layers of 
 * the application, particularly within session and view scopes. It uses Jakarta EE 
 * annotations to manage the scope of the data between views.
 * 
 * @file PhysicianPojo.java
 * @version 1.0
 * @since 2024-10-04
 * @author Robin Phillis
 */
package databank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.faces.view.ViewScoped;

/**
 * Represents a physician entity in the application, containing details such as 
 * the physician's ID, first name, last name, email, phone number, specialty, 
 * and the creation date of the record. This class is used to encapsulate physician 
 * data in various operations across the application, particularly in the context 
 * of CRUD operations.
 * 
 * The class is annotated with {@code @ViewScoped}, ensuring that the data persists 
 * across multiple HTTP requests during the same JSF view. The class implements 
 * {@code Serializable} to allow the physician data to be transferred and stored 
 * efficiently across different layers of the application.
 * 
 * The class overrides {@code equals()}, {@code hashCode()}, and {@code toString()} 
 * methods to ensure that physician objects can be compared, hashed, and printed 
 * correctly.
 */

@ViewScoped
public class PhysicianPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	// Physician attributes
	protected int id;
	protected String lastName;
	protected String firstName;
	protected String email;
	protected String phone;
	protected String specialty;
	protected LocalDateTime created;

	/**
	 * Default constructor, creates an empty physician object.
	 */
	public PhysicianPojo() 
	{
		super();
	}

	/**
	 * Getters for accessing physician attributes.
	 * These methods return the current values of the physician's ID, first name, 
	 * last name, email, phone number, specialty, and the date the record was created.
	 * 
	 * @return the respective attribute of the physician.
	 */
	public int getId() 
	{
		return id;
	}

	public String getFirstName() 
	{
		return firstName;
	}

	public String getLastName() 
	{
		return lastName;
	}

	public String getEmail() 
	{
		return email;
	}

	public String getPhoneNumber() 
	{
		return phone;
	}

	public String getSpecialty() 
	{
		return specialty;
	}

	public LocalDateTime getCreated() 
	{
		return created;
	}

	/**
	 * Setters for modifying physician attributes.
	 * These methods allow modification of the physician's ID, first name, 
	 * last name, email, phone number, specialty, and the date the record was created.
	 * 
	 * @param value the new value to be assigned to the respective attribute.
	 */
	public void setId(int id) 
	{
		this.id = id;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}

	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) 
	{
		this.phone = phoneNumber;
	}

	public void setSpecialty(String specialty) 
	{
		this.specialty = specialty;
	}

	public void setCreated(LocalDateTime created) 
	{
		this.created = created;
	}

	/**
	 * Generates a hash code for the physician object based on the physician's ID.
	 * This method is used to ensure that physician objects can be hashed correctly
	 * in collections such as hash tables.
	 * 
	 * @return the hash code value for the physician object.
	 */
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = super.hashCode();
		return prime * result + Objects.hash(getId());
	}

	/**
	 * Compares this physician object to another object to determine equality.
	 * Two physician objects are considered equal if they have the same ID.
	 * 
	 * @param obj the object to compare to this physician.
	 * @return {@code true} if the two objects are equal, {@code false} otherwise.
	 */
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj) 
			return true;
		
		if (obj == null) 
			return false;
		
		if (obj instanceof PhysicianPojo otherPhysicianPojo) 
			return Objects.equals(this.getId(), otherPhysicianPojo.getId());
		
		return false;
	}

	/**
	 * Returns a string representation of the physician object, containing key
	 * attributes such as ID, first name, last name, email, phone number, and specialty.
	 * This method is useful for debugging and logging purposes.
	 * 
	 * @return a string representation of the physician object.
	 */
	@Override
	public String toString() 
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Physician [id = ").append(getId()).append(", ");
		
		if (getFirstName() != null) 
			builder.append("firstName = ").append(getFirstName()).append(", ");
		
		if (getLastName() != null) 
			builder.append("lastName = ").append(getLastName()).append(", ");
		
		if (getEmail() != null) 
			builder.append("email = ").append(getEmail()).append(", ");
		
		if (getPhoneNumber() != null) 
			builder.append("phoneNumber = ").append(getPhoneNumber()).append(", ");
		
		if (getSpecialty() != null) 
			builder.append("specialty = ").append(getSpecialty()).append(", ");
				
		builder.append("]");
		return builder.toString();
	}
}
