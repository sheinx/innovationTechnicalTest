package com.innovation.logicalview;

import java.util.Collection;
import java.util.List;

import com.hazelcast.core.IMap;
import com.innovation.persistence.data.User;
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
	 * Find a User filtering by the name
	 * @param name
	 * @return
	 */
	List<User> findByName(String name);
	
	/**
	 * Find a User filtering by the phone
	 * @param phone
	 * @return
	 */
	List<User> findByPhone(String phone);
	
	/**
	 * Find a User filtering by the company
	 * @param company
	 * @return
	 */
	List<User> findByCompany(String company);
	
	/**
	 * Find a User filtering by the siret
	 * @param siret
	 * @return
	 */
	List<User> findBySiret(String siret);

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
	 * Method which is in charge of Initialize the Users in the Hazelcast Map
	 * @author sheinx
	 * @since Innovation Technical Test
	 * @return IMap - All Users
	 */
	IMap<Long,User> initializeHazelcast();
	
	/**
	 * Method which is in charge of filtering The IMAP depending on a filter getted.
	 * @param filter
	 * @param usersMap
	 * @return
	 */
	public Collection<User> searchByFilter(String filter,IMap usersMap);
}
