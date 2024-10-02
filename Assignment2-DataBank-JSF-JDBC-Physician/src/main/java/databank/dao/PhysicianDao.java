/*********************************************************************************************************
 * File:  PhysicianDao.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shariar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.dao;

import java.util.List;

import databank.model.PhysicianPojo;

/**
 * Description:  API for the database C-R-U-D operations
 */
public interface PhysicianDao {

	// C
	public PhysicianPojo createPhysician(PhysicianPojo physician);

	// R
	public PhysicianPojo readPhysicianById(int physicianId);

	public List<PhysicianPojo> readAllPhysicians();

	// U
	public void updatePhysician(PhysicianPojo physician);

	// D
	public void deletePhysicianById(int physicianId);

}