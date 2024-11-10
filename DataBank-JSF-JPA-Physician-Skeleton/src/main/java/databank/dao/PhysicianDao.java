/********************************************************************************************************2*4*w*
 * File:  PhysicianDao.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.util.List;

import databank.model.PhysicianPojo;

/**
 * Description:  API for the database C-R-U-D operations
 */
public interface PhysicianDao {

	List<PhysicianPojo> readAllPhysicians();

	// C
	PhysicianPojo createPhysician(PhysicianPojo physician);

	// R
	PhysicianPojo readPhysicianById(int physicianId);

	// U
	PhysicianPojo updatePhysician(PhysicianPojo physician);

	// D
	void deletePhysicianById(int physicianId);

}
