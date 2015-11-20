package com.innovation.persistence.interfaces;

import java.util.List;

import com.innovation.persistence.dao.User;
/**
 * Interface of the Users Services
 * Whole the method related to Users are stored in this class
 * @author sheinx
 * @since Innovation Technical Test
 */
public interface UsersServices {
	/**
	 * Find a User filtering by the ID
	 * @param id
	 * @return
	 */
	User findById(Long id);

	/**
	 * Persist a user in the DB
	 * @param customer
	 * @return
	 */
	User save(User customer);

	/**
	 * Get all the users
	 * @return
	 */
	List<User> findAll();

	/**
	 * Get all users paged
	 * @param page
	 * @param pageSize
	 * @return
	 */
	List<User> findAll(int page, int pageSize);
}
