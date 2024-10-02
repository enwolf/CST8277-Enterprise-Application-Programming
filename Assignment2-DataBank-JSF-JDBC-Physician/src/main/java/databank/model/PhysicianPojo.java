/*********************************************************************************************************
 * File:  PhysicianPojo.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.faces.view.ViewScoped;

/**
 *
 * Description:  Model for the Physician object <br>
 * A little read about @ViewScoped <br>
 * https://stackoverflow.com/a/6026009/764951
 */
@ViewScoped
public class PhysicianPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String lastName;
	protected String firstName;
	protected String email;
	protected String phoneNumber;
	protected String specialty;
	protected LocalDateTime created;

	public PhysicianPojo() {
		super();
	}

	public int getId() {
		return id;
	}

	/**
	 * @param id new value for id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the value for firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName new value for firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the value for lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName new value for lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	//TODO Add getter and setter methods for the specialty field here
	
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public LocalDateTime getCreated() {
		return created;
	}
	
	// Use getter's for member variables
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		// Only include member variables that really contribute to an object's identity
		// i.e. if variables like version/updated/name/etc. change throughout an object's lifecycle,
		// they shouldn't be part of the hashCode calculation
		return prime * result + Objects.hash(getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}

		/* enhanced instanceof - yeah!
		 * As of JDK 14, no need for additional 'silly' cast:
		    if (animal instanceof Cat) {
		        Cat cat = (Cat) animal;
		        cat.meow();
                // Other class Cat operations ...
            }
         * Technically, 'otherPhysicianPojo' is a <i>pattern</i> that becomes an in-scope variable binding.
         * Note:  Need to watch out just-in-case there is already a 'otherPhysicianPojo' variable in-scope!
		 */
		if (obj instanceof PhysicianPojo otherPhysicianPojo) {
			// See comment (above) in hashCode():  Compare using only member variables that are
			// truly part of an object's identity
			return Objects.equals(this.getId(), otherPhysicianPojo.getId());
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Physician [id = ").append(getId()).append(", ");
		if (getFirstName() != null) {
			builder.append( "firstName = ").append(getFirstName()).append(", ");
		}
		if (getLastName() != null) {
			builder.append("lastName = ").append(getLastName()).append(", ");
		}
		if (getEmail() != null) {
			builder.append("email = ").append(getEmail()).append(", ");
		}
		if (getPhoneNumber() != null) {
			builder.append("phoneNumber = ").append(getPhoneNumber()).append(", ");
		}
		//TODO Add code to append the specialty field
		builder.append("]");
		return builder.toString();
	}

}
