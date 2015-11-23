package com.innovation.persistence.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.innovation.persistence.data.User;

/**
 * Interface mandatory in order to run CRUD operations
 * 
 * @author sheinx
 *
 */
@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {

	public User findById(Long id);

	public List<User> findByName(String name);

	public List<User> findByPhone(String phone);

	public List<User> findByCompany(String company);

	public List<User> findBySiret(String siret);

}
