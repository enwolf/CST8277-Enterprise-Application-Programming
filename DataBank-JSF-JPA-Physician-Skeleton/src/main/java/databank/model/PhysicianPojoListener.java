/********************************************************************************************************2*4*w*
 * File:  PhysicianPojoListener.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.model;

import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;


/**
 * Entity listener class for PhysicianPojo to set creation and update timestamps.
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
public class PhysicianPojoListener {

    /**
     * Called before persisting a PhysicianPojo entity to set the created and updated timestamps.
     *
     * @param physician the PhysicianPojo entity being persisted.
     */
    @PrePersist
    public void setCreatedOnDate(PhysicianPojo physician) {
        LocalDateTime now = LocalDateTime.now();
        physician.setCreated(now);
        // Also set the updated date
        physician.setUpdated(now);
    }

    /**
     * Called before updating a PhysicianPojo entity to set the updated timestamp.
     *
     * @param physician the PhysicianPojo entity being updated.
     */
    @PreUpdate
    public void setUpdatedDate(PhysicianPojo physician) {
        physician.setUpdated(LocalDateTime.now());
    }
}