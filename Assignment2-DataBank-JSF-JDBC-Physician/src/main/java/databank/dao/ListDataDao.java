/*********************************************************************************************************
 * File:  ListDataDao.java Course Materials CST8277
 *
 * @author Teddy Yap
 */
package databank.dao;

import java.util.List;

/**
 * Description:  API for reading list data from the database
 */
public interface ListDataDao {

	public List<String> readAllSpecialties();

}