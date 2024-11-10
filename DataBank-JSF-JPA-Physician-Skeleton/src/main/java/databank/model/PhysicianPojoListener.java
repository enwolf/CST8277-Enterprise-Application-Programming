/********************************************************************************************************2*4*w*
 * File:  PhysicianPojoListener.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.model;

import java.time.LocalDateTime;

/**
 * TODO 21 - What annotations should be used on these methods?
 * https://www.logicbig.com/tutorials/java-ee-tutorial/jpa/entity-listeners.html<br>
 */
public class PhysicianPojoListener {

	//TODO 22 - Called before persist to add the dates
	public void setCreatedOnDate(PhysicianPojo physician) {
		LocalDateTime now = LocalDateTime.now();
		physician.setCreated(now);
		//Might as well call setUpdated as well
		physician.setUpdated(now);
	}

	//TODO 23 - Called before update to update the date
	public void setUpdatedDate(PhysicianPojo physician) {
		physician.setUpdated(LocalDateTime.now());
	}

}
