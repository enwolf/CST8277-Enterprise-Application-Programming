package databank.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.ServletContext;

/**
 * Implementation of the ListDataDao interface for reading list data from the database.
 * Provides methods to read a list of specialties.
 * 
 * @author Robin Phillis
 * @version 1.0
 * @since 11/10/2024
 */
@Named
@ApplicationScoped
public class ListDataDaoImpl implements ListDataDao, Serializable {
    /** Explicitly set serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** SQL query to read all specialties from the database */
    private static final String READ_ALL_SPECIALTIES = "SELECT specialty FROM specialties";

    /** The EntityManager used for database operations */
    @PersistenceContext(name = "PU_DataBank")
    protected EntityManager entityManager;

    /** The ExternalContext for logging purposes */
    @Inject
    protected ExternalContext externalContext;

    /**
     * Logs a message to the servlet context.
     * 
     * @param msg the message to log
     */
    private void logMsg(String msg) 
    {
        ((ServletContext) externalContext.getContext()).log(msg);
    }

    /**
     * Reads all specialties from the database and returns them as a list of strings.
     * 
     * @return a list of specialties as strings, or an empty list if an exception occurs
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> readAllSpecialties() 
    {
        logMsg("reading all specialties");
        List<String> specialties = new ArrayList<>();
        try 
        {
            specialties = (List<String>) entityManager.createNativeQuery(READ_ALL_SPECIALTIES).getResultList();
        }
        catch (Exception e) 
        {
            logMsg("something went wrong: " + e.getLocalizedMessage());
        }
        return specialties;
    }
}
