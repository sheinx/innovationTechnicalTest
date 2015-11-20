package com.innovation.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.innovation.persistence.dao.User;

/**
 * Interface mandatory in order to run CRUD operations
 * @author sheinx
 *
 */
public interface UserRepository extends CrudRepository<User, Long> {

}
