/*********************************************************************************************************
 * File:  Physician.java Course Materials CST8277
 * 
 * @author Teddy Yap
 */
package jdbccmd;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Simple POJO for physician.
 * 
 * @author Teddy Yap
 */
public class Physician implements Serializable {
	private static final long serialVersionUID = 1L;

	protected int id;
	protected String lastName;
	protected String firstName;
	protected String email;
	protected String phoneNumber;
	//TODO Add the specialty field
	protected LocalDateTime created;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
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

	//TODO Add getter and setter methods for the specialty field

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Physician)) {
			return false;
		}
		Physician other = (Physician) obj;
		return id != other.id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Physician[id = ").append(id);
		if (lastName != null) {
			builder.append(", lastName = ").append(lastName);
		}
		if (firstName != null) {
			builder.append(", firstName = ").append(firstName);
		}
		if (email != null) {
			builder.append(", email = ").append(email);
		}
		if (phoneNumber != null) {
			builder.append(", phoneNumber = ").append(phoneNumber);
		}
		//TODO Append the specialty field
		if (created != null) {
			builder.append(", created = ").append(created);
		}
		return builder.append("]").toString();
	}
	
}
