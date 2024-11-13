package databank.jsf;

import java.io.Serializable;
import java.time.LocalDateTime;
import databank.model.PhysicianPojo;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

/**
 * This class represents the scope of adding a new physician to the DB.
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@Named("newPhysician")
@ViewScoped
public class NewPhysicianView implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected String lastName;
    protected String firstName;
    protected String email;
    protected String phoneNumber;
    protected String specialty;
    protected LocalDateTime created;
    protected LocalDateTime updated;
    protected int version = 1; // Default value

    @Inject
    @ManagedProperty("#{physicianController}")
    protected PhysicianController physicianController;

    public NewPhysicianView() {
    }

    // Getters and setters for all fields
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public int getVersion() {
        return version;
    }

    public void addPhysician() {
        if (allNotNullOrEmpty(firstName, lastName, email, phoneNumber, specialty)) {
            PhysicianPojo theNewPhysician = new PhysicianPojo();
            theNewPhysician.setFirstName(getFirstName());
            theNewPhysician.setLastName(getLastName());
            theNewPhysician.setEmail(getEmail());
            theNewPhysician.setPhoneNumber(getPhoneNumber());
            theNewPhysician.setSpecialty(getSpecialty());
            // Call the controller to add the new physician
            physicianController.addNewPhysician(theNewPhysician);
            // Clean up
            physicianController.toggleAdding();
            setFirstName(null);
            setLastName(null);
            setEmail(null);
            setPhoneNumber(null);
            setSpecialty(null);
        }
    }

    static boolean allNotNullOrEmpty(final Object... values) 
    {
        if (values == null) 
        {
            return false;
        }
        
        for (final Object val : values) 
        {
            if (val == null) 
            {
                return false;
            }
            
            if (val instanceof String) 
            {
                String str = (String) val;
                if (str.isEmpty()) 
                {
                    return false;
                }
            }
        }
        return true;
    }
}
