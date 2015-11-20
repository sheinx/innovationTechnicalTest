package com.innovation.logicalview;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.innovation.persistence.dao.User;
import com.innovation.persistence.repository.UserRepository;

/**
 * Logical View Class.
 * This class is in charge of handle all the logical process of the application.
 * Handle all User business process
 * @since Innovation Technical Test
 * @author sheinx
 *
 */
public class UserLVImpl {

	// define the attributes and injection necessary
	@Autowired
	private UserRepository userRepository;
	
	public void getAllUser(){
		// Inicialize Hazelcast
		Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
        //Create the HazelcastMap
        Map<Long, User> mapUsers = instance.getMap("users");
        
        // get the all users and iterate it in order to load the HazelCast cache
        List<User> allUsers = (ArrayList<User>)userRepository.findAll();
        for ( User u : allUsers ){
        	// Store User ID and the User
        	mapUsers.put(u.getId(), u);
        }
	}
}
