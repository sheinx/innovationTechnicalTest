package com.innovation.persistence.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.innovation.persistence.dao.User;
import com.innovation.persistence.interfaces.UsersServices;
/**
 * User Service Implementation
 * @author sheinx
 *
 */
@Repository
public class UsersServicesImpl implements UsersServices {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public User findById(Long id){
		return entityManager.find(User.class, id);
	}
	
	public User save(User user){
		User result = user;
		if (user.getId() == null) {
			entityManager.persist(user);
		} else {
			if (!entityManager.contains(user)) {
				result = entityManager.merge(user);
			}
		}
		return result;
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> findAll(int page, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}