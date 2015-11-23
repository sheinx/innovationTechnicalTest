package com.innovation.logicalview;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.innovation.controller.UserController;
import com.innovation.persistence.data.User;
import com.innovation.persistence.repository.UserRepository;

/**
 * User Service Implementation
 * 
 * @author sheinx
 *
 */
@Service("userServices")
@Transactional
public class UsersServicesImpl implements UsersServices {

	@Autowired
	private UserRepository userRepository;
	
	static Logger LOGGER = LoggerFactory.getLogger(UsersServicesImpl.class);

	public List<User> findByName(String name) {
		List<User> users = userRepository.findByName(name);
		return users;
	}

	public List<User> findByPhone(String phone) {
		List<User> users = userRepository.findByCompany(phone);
		return users;
	}

	public List<User> findByCompany(String company) {
		List<User> users = userRepository.findByCompany(company);
		return users;
	}

	public List<User> findBySiret(String siret) {
		List<User> users =userRepository.findBySiret(siret);
		return users;
	}

	public User findById(Long id) {
		User user = userRepository.findById(id);
		return user;
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	public List<User> findAll() {
		List<User> users =  (List<User>) userRepository.findAll();
		return users;
	}

	public IMap<Long, User> initializeHazelcast() {
		List<User> users = (List<User>) userRepository.findAll();
		Config cfg = new Config();
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		IMap<Long, User> usersMap = instance.getMap("usersMap");
		usersMap.addIndex("id", true);
		usersMap.addIndex("name", true);
		usersMap.addIndex("phone", true);
		usersMap.addIndex("company", true);
		usersMap.addIndex("siret", true);
		for (User u : users) {
			usersMap.put(u.getId(), u);
		}
		return usersMap;
	}
	
	public Collection<User> searchByFilter(String filter,IMap usersMap){
		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate predicate = (e.get("name").equal("filter").or(e.get("phone").equal(filter)).or(e.get("company").equal(filter)).or(e.get("siret").equal(filter)));
		Collection<User> usuarios = usersMap.values(predicate);
		return usuarios;
	}

}