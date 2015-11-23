package com.innovation.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.innovation.logicalview.DataTablesResponse;
import com.innovation.logicalview.UsersServices;
import com.innovation.persistence.data.User;

/**
 * Controller which is in charge of Handle the Users
 * @author sheinx
 *
 */
@Controller
public class UserController {

	@Autowired
	private UsersServices userServices;

	private IMap<Long, User> usersMap;
	
	private List<User> usuariosFiltrados;
	
	private List<User> totalUsers;
	
	private List<User> usuariosPaginados;

	static Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request) {
		// Set the page which will be redirect
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		// Initialize Hazelcast
		LOGGER.debug("Initialize the IMAP with all user in Hazelcast Istance");
		Set<HazelcastInstance> instancias = Hazelcast.getAllHazelcastInstances();
		if (instancias == null || instancias.isEmpty()) {
			usersMap = userServices.initializeHazelcast();
			totalUsers = userServices.initializeHazelcastList(usersMap);
		} else {
			HazelcastInstance instancia = Hazelcast.getHazelcastInstanceByName("instance");
			usersMap = instancia.getMap("usersMap");
		}
		LOGGER.info("All the user are in Hazelcast Instance");
		return model;
	}

	/**
	 * Mapping to load the datatables
	 * @param orderColIndex
	 * @param orderDir
	 * @param start
	 * @param length
	 * @param searchValue
	 * @param allRequestParams
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public @ResponseBody String searchAction(
			@RequestParam(value = "order[0][column]", defaultValue = "0") String orderColIndex,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String orderDir,
			@RequestParam(defaultValue = "0") int start, 
			@RequestParam(defaultValue = "20") int length,
			@RequestParam(value = "search[value]") String searchValue,
			@RequestParam Map<String,String> allRequestParams ) {

		// total number of elements
		Integer totalSize = 0;
		
		// Check if we need filter or not
		if ( StringUtils.equals(searchValue, "") ){
			// get the user paginated
			usuariosPaginados = userServices.getPaginatedUser(totalUsers,start,length);
			// set the total
			totalSize = totalUsers.size();
		}else{
			// get the user paginated
			setUsuariosFiltrados(userServices.searchByFilter(searchValue, usersMap));
			usuariosPaginados = userServices.getPaginatedUser(usuariosFiltrados,start,length);
			totalSize = usuariosFiltrados.size();
		}

		// Create the Wrapper Object need for the datatables and set the properties
		DataTablesResponse<User> datatablesResponse = new DataTablesResponse<User>();
		datatablesResponse.setData(this.usuariosPaginados);
		datatablesResponse.setRecordsTotal(Long.valueOf(totalSize));
		datatablesResponse.setRecordsFiltered(Long.valueOf(totalSize));

		// Create the JSON Object
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json2 = gson.toJson(datatablesResponse);

		return json2;
	}

	// GETTERS AND SETTERS

	public UsersServices getUserServices() {
		return userServices;
	}

	public void setUserServices(UsersServices userServices) {
		this.userServices = userServices;
	}

	public IMap<Long, User> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(IMap<Long, User> usersMap) {
		this.usersMap = usersMap;
	}

	public List<User> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}

	public void setUsuariosFiltrados(List<User> usuariosFiltrados) {
		this.usuariosFiltrados = usuariosFiltrados;
	}

	public List<User> getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(List<User> totalUsers) {
		this.totalUsers = totalUsers;
	}

	public List<User> getUsuariosPaginados() {
		return usuariosPaginados;
	}

	public void setUsuariosPaginados(List<User> usuariosPaginados) {
		this.usuariosPaginados = usuariosPaginados;
	}
	
	

}
