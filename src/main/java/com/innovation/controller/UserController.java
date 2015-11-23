package com.innovation.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.innovation.logicalview.DataTablesResponse;
import com.innovation.logicalview.UsersServices;
import com.innovation.persistence.data.User;


@Controller
public class UserController {
	
	@Autowired
	private UsersServices userServices;
	
	private IMap<Long,User> usersMap;

	static Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		// Set the page which will be redirect
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		//TODO: poner solo 1 instancia y recuperarla
		// Initialize Hazelcast
		LOGGER.debug("Initialize the IMAP with all user in Hazelcast Istance");
		Set<HazelcastInstance> instancias = Hazelcast.getAllHazelcastInstances();
		if ( instancias == null || instancias.isEmpty() ){
			usersMap=userServices.initializeHazelcast();
		}
		LOGGER.info("All the user are in Hazelcast Instance");
		return model;
	}
	

	@RequestMapping(value = "/searche", method = RequestMethod.GET)
	public @ResponseBody DataTablesResponse<User> searchAction(
			@RequestParam(value="order[0][column]", defaultValue = "0") String orderColIndex,
			@RequestParam(value="order[0][dir]", defaultValue = "asc") String orderDir,
			@RequestParam(defaultValue = "0") int start,
			@RequestParam(defaultValue = "2") int length)			 
	{					
		
		//Nos traemos la página solicitada de BBDD.
		Collection<User> usuariosFiltrados = userServices.searchByFilter("A Aliquet Corp.", usersMap);
		List<User> listUser = new ArrayList<User>(usuariosFiltrados);
		
		//Creamos y rellenamos la respuesta.
		DataTablesResponse<User> datatablesResponse = new DataTablesResponse<User>();
		datatablesResponse.setData(listUser);
		datatablesResponse.setRecordsTotal(Long.valueOf(listUser.size()));
		datatablesResponse.setRecordsFiltered(Long.valueOf(listUser.size()));
		
		return datatablesResponse;	
	}
	
	

	// GETTERS AND SETTERS
	
	public UsersServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UsersServices userServices) {
		this.userServices = userServices;
	}

	public IMap<Long,User> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(IMap<Long,User> usersMap) {
		this.usersMap = usersMap;
	}
	
	
	

}
