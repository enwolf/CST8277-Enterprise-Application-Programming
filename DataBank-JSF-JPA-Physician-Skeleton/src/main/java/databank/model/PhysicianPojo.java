/********************************************************************************************************2*4*w*
 * File:  PhysicianPojo.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.faces.view.ViewScoped;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;

/**
 * TODO 14.1 - Complete the @Entity with correct name.<br>
 * TODO 14.2 - Complete the two @NamedQueries.<br>
 * TODO 15 - Use the correct table name.<br>
 * TODO 16 - Fix the AccessType.<br>
 * TODO 17 - Make PhysicianPojoListener be the listener of this class.  Refer to:
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/entity-listeners.html<br>
 * TODO 18.1 - Add the specialty field, add getter and setter, and update toString method to include the specialty field.<br>
 * TODO 18.2 - Add the remaining @Basic and @Column to all the fields.<br>
 * TODO 19 - Use @Version on the correct field.  This annotation helps keeping track of what version of entity we are
 * working with.<br>
 * TODO 20 - Dates (LocalDateTime) are to be mapped and 'editable' field is not to be mapped.
 */


/**
 * Represents a physician entity in the databank system.
 * This class is a JPA entity used for database persistence and includes fields
 * for tracking physician details such as name, contact information, and timestamps.
 * <p>
 * It includes annotations for JPA mappings and named queries.
 * </p>
 *
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@ViewScoped
@Entity(name = "Physician")
@Table(name = "physician", catalog = "databank", schema = "databank")
@Access(AccessType.FIELD)
@EntityListeners(PhysicianPojoListener.class)
@NamedQuery(name = PhysicianPojo.PHYSICIAN_FIND_ALL, query = "SELECT p FROM Physician p")
@NamedQuery(name = PhysicianPojo.PHYSICIAN_FIND_ID, query = "SELECT p FROM Physician p WHERE p.id = :id")
public class PhysicianPojo implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Query constant to find all physicians. */
    public static final String PHYSICIAN_FIND_ALL = "Physician.findAll";

    /** Query constant to find a physician by ID. */
    public static final String PHYSICIAN_FIND_ID = "Physician.findById";

    /** The unique identifier for a physician. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected int id;

    /** The last name of the physician. */
    @Basic
    @Column(name = "last_name")
    protected String lastName;

    /** The first name of the physician. */
    @Basic
    @Column(name = "first_name")
    protected String firstName;

    /** The email address of the physician. */
    @Basic
    @Column(name = "email")
    protected String email;

    /** The phone number of the physician. */
    @Basic
    @Column(name = "phone")
    protected String phoneNumber;

    /** The creation timestamp of the physician record. */
    @Basic
    @Column(name = "created", updatable = false)
    protected LocalDateTime created;

    /** The last updated timestamp of the physician record. */
    @Basic
    @Column(name = "updated")
    protected LocalDateTime updated;

    /** The specialty of the physician. */
    @Column(name = "specialty")
    protected String specialty;

    /** The version number for optimistic locking. */
    @Version
    @Column(name = "version")
    protected int version;

    /** A transient field indicating if the physician is editable (not persisted). */
    @Transient
    protected boolean editable;

    /** Default constructor. */
    public PhysicianPojo() {
        super();
    }

    /**
     * Gets the ID of the physician.
     *
     * @return the physician ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the physician.
     *
     * @param id the new physician ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the last name of the physician.
     *
     * @return the last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the physician.
     *
     * @param lastName the new last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name of the physician.
     *
     * @return the first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the physician.
     *
     * @param firstName the new first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the email address of the physician.
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the physician.
     *
     * @param email the new email address.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the physician.
     *
     * @return the phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the physician.
     *
     * @param phoneNumber the new phone number.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the creation timestamp of the physician record.
     *
     * @return the creation timestamp.
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Sets the creation timestamp of the physician record.
     *
     * @param created the new creation timestamp.
     */
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    /**
     * Gets the last updated timestamp of the physician record.
     *
     * @return the updated timestamp.
     */
    public LocalDateTime getUpdated() {
        return updated;
    }

    /**
     * Sets the last updated timestamp of the physician record.
     *
     * @param updated the new updated timestamp.
     */
    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }

    /**
     * Gets the version number for optimistic locking.
     *
     * @return the version number.
     */
    public int getVersion() {
        return version;
    }

    /**
     * Sets the version number for optimistic locking.
     *
     * @param version the new version number.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * Gets the specialty of the physician.
     *
     * @return the specialty.
     */
    public String getSpecialty() {
        return specialty;
    }

    /**
     * Sets the specialty of the physician.
     *
     * @param specialty the new specialty.
     */
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    /**
     * Checks if the physician is editable.
     *
     * @return true if editable, false otherwise.
     */
    public boolean getEditable() {
        return editable;
    }

    /**
     * Sets the editable state of the physician.
     *
     * @param editable the new editable state.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Generates a hash code for the physician entity.
     *
     * @return the hash code.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        return prime * Objects.hash(getId());
    }

    /**
     * Checks if this PhysicianPojo object is equal to another object.
     *
     * @param obj the object to compare with.
     * @return true if the objects are the same or have the same ID, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PhysicianPojo otherPhysicianPojo = (PhysicianPojo) obj;
        return Objects.equals(this.getId(), otherPhysicianPojo.getId());
    }

    /**
     * Returns a string representation of the PhysicianPojo object.
     *
     * @return a string containing the physician's details, including ID, name, phone number, email, specialty, and timestamps.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Physician [id = ").append(getId());
        if (getLastName() != null) {
            builder.append(", lastName = ").append(getLastName());
        }
        if (getFirstName() != null) {
            builder.append(", firstName = ").append(getFirstName());
        }
        if (getPhoneNumber() != null) {
            builder.append(", phoneNumber = ").append(getPhoneNumber());
        }
        if (getEmail() != null) {
            builder.append(", email = ").append(getEmail());
        }
        if (getSpecialty() != null) {
            builder.append(", specialty = ").append(getSpecialty());
        }
        if (getCreated() != null) {
            builder.append(", created = ").append(getCreated());
        }
        if (getUpdated() != null) {
            builder.append(", updated = ").append(getUpdated());
        }
        builder.append("]");
        return builder.toString();
    }

}
