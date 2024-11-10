/********************************************************************************************************2*4*w*
 * File:  PhysicianController.java Course Materials CST8277
 *
 * @author Teddy Yap
 * @author Shahriar (Shawn) Emami
 * @author (original) Mike Norman
 */
package databank.jsf;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import databank.dao.ListDataDao;
import databank.dao.PhysicianDao;
import databank.model.PhysicianPojo;

/**
 * <p>
 * Description:  Responsible for collection of PhysicianPojo's in XHTML (list) <h:dataTable> </br>
 * Delegates all C-R-U-D behavior to DAO
 * </p>
 * 
 * <p>
 * This class is complete.
 * </p>
 */
@Named("physicianController")
@SessionScoped
public class PhysicianController implements Serializable {
	private static final long serialVersionUID = 1L;

	//Get the log4j2 logger for this class
	private static final Logger LOG = LogManager.getLogger();

	public static final String UICONSTS_BUNDLE_EXPR = "#{uiconsts}";
	public static final String PHYSICIAN_MISSING_REFRESH_BUNDLE_MSG = "refresh";
	public static final String PHYSICIAN_OUTOFDATE_REFRESH_BUNDLE_MSG = "outOfDate";

	@Inject
	protected FacesContext facesContext;

	@Inject
	protected PhysicianDao physicianDao;

	@Inject
	protected ListDataDao listDataDao;

	@Inject
	@ManagedProperty(UICONSTS_BUNDLE_EXPR)
	protected ResourceBundle uiconsts;

	protected List<PhysicianPojo> physicians;
	//Boolean used for toggling the rendering of add physician in index.xhtml
	protected boolean adding;

	public void loadPhysicians() {
		LOG.debug("loadPhysicians");
		physicians = physicianDao.readAllPhysicians();
	}

	public List<PhysicianPojo> getPhysicians() {
		return this.physicians;
	}

	public void setPhysicians(List<PhysicianPojo> physicians) {
		this.physicians = physicians;
	}

	public boolean isAdding() {
		return adding;
	}

	public void setAdding(boolean adding) {
		this.adding = adding;
	}

	/**
	 * Toggles the add physician mode which determines whether the addPhysician form is rendered
	 */
	public void toggleAdding() {
		this.adding = !this.adding;
	}

	public String editPhysician(PhysicianPojo physician) {
		LOG.debug("editPhysician = {}", physician);
		physician.setEditable(true);
		return null; //Stay on current page
	}

	public String updatePhysician(PhysicianPojo physicianWithEdits) {
		LOG.debug("updatePhysician = {}", physicianWithEdits);
		PhysicianPojo physicianToBeUpdated = physicianDao.readPhysicianById(physicianWithEdits.getId());
		if (physicianToBeUpdated == null) {
			LOG.debug("FAILED update physician, does not exists = {}", physicianToBeUpdated);
			//If the physician being updated does not exists, send an error message to index.xhtml
			//<h:messages globalOnly="true" layout="table" styleClass="alert alert-danger"/>
			//null clientId means it is global not for a specific h:message
			//Someone else deleted it
			facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					uiconsts.getString(PHYSICIAN_MISSING_REFRESH_BUNDLE_MSG), null));
		} else {
			physicianToBeUpdated = physicianDao.updatePhysician(physicianWithEdits);
			if (physicianToBeUpdated == null) {
				LOG.debug("FAILED update physician = {}", physicianToBeUpdated);
				//It could be the transaction is already been committed
				//OptimisticLockException 
				facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						uiconsts.getString(PHYSICIAN_OUTOFDATE_REFRESH_BUNDLE_MSG), null));
			} else {
				physicianToBeUpdated.setEditable(false);
				int idx = physicians.indexOf(physicianWithEdits);
				physicians.remove(idx);
				physicians.add(idx, physicianToBeUpdated);
			}
		}
		return null; //Stay on current page
	}

	public String cancelUpdate(PhysicianPojo physician) {
		LOG.debug("cancelUpdate = {}", physician);
		physician.setEditable(false);
		return null; //Stay on current page
	}

	public void deletePhysician(int physicianId) {
		LOG.debug("deletePhysician = {}", physicianId);
		PhysicianPojo physicianToBeRemoved = physicianDao.readPhysicianById(physicianId);
		if (physicianToBeRemoved == null) {
			LOG.debug("failed deletePhysician does not exists = {}", physicianId);
			return;
		}
		physicianDao.deletePhysicianById(physicianId);
		physicians.remove(physicianToBeRemoved);
	}

	public void addNewPhysician(PhysicianPojo physician) {
		LOG.debug("adding new physician = {}", physician);
		PhysicianPojo newPhysician = physicianDao.createPhysician(physician);
		physicians.add(newPhysician);
	}

	public String refreshPhysicianForm() {
		LOG.debug("refreshPhysicianForm");
		//Clear all messaged in facesContext first
		Iterator<FacesMessage> facesMessageIterator = facesContext.getMessages();
		while (facesMessageIterator.hasNext()) {
			facesMessageIterator.remove();
		}
		return "index.xhtml?faces-redirect=true";
	}

	public List<String> getSpecialties() {
		return listDataDao.readAllSpecialties();
	}
	
}
