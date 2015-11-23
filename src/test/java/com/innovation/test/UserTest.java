package com.innovation.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.query.EntryObject;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.PredicateBuilder;
import com.innovation.logicalview.UsersServices;
import com.innovation.persistence.data.User;
import com.innovation.utils.UserComparator;

@ContextConfiguration("/mvc-dispatcher-servlet-Test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

	@Autowired
	private UsersServices usersServices;

	@Test
	public void testAllUser() {
		Long empieza = System.currentTimeMillis();
		List<User> users = usersServices.findAll();
		Long acaba = System.currentTimeMillis();
		System.out.println("Time expend:" + (acaba - empieza));
		assertEquals(150000, users.size());
	}

	@Test
	public void testfindById() {
		User user = usersServices.findById(new Long(1));
		if (user != null) {
			assertEquals("Ralph May", user.getName());
		}
	}

	@Test
	public void testfindByName() {
		List<User> users = usersServices.findByName("Ralph May");
		if (users != null && !users.isEmpty()) {
			// must to return just a row
			assertEquals("Ralph May", users.get(0).getName());
		}
	}

	@Test
	public void testfindByPhone() {
		List<User> users = usersServices.findByPhone("033-729-8967");
		if (users != null && !users.isEmpty()) {
			// return just one row
			assertEquals("033-729-8967", users.get(0).getPhone());
		}
	}

	@Test
	public void testfindByCompany() {
		List<User> users = usersServices.findByCompany("A Aliquet Corp.");
		if (users != null && !users.isEmpty()) {
			// Must to return 6 rows
			assertEquals(6, users.size());
		}
	}

	@Test
	public void testfindBySiret() {
		List<User> users = usersServices.findBySiret("007045388-00001");
		if (users != null && !users.isEmpty()) {
			// return just one row
			assertEquals("007045388-00001", users.get(0).getSiret());
		}
	}

	@Test
	public void testFakefindBySiret() {
		List<User> users = usersServices.findBySiret("HelloSiret");
		if (users != null && !users.isEmpty()) {
			// return just one row
			assertNotEquals("007045388-00001", users.get(0).getSiret());
		}
	}

	@Test
	public void testInitializeHazelcast() {
		List<User> users = usersServices.findAll();
		Config cfg = new Config();
		cfg.setInstanceName("InstanceNode1");
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		
		IMap<Long, User> usersMap = instance.getMap("usersMap");
		MapConfig userMapConfig = cfg.getMapConfig("usersMap");
		userMapConfig.setOptimizeQueries(true);
		usersMap.addIndex("id", true);
		usersMap.addIndex("name", true);
		usersMap.addIndex("phone", true);
		usersMap.addIndex("company", true);
		usersMap.addIndex("siret", true);
		for (User u : users) {
			usersMap.put(u.getId(), u);
		}
		Long empieza = System.currentTimeMillis();
		Collection<User> colCustomers = usersMap.values();
		Long acaba = System.currentTimeMillis();
		System.out.println("Time expend:" + (acaba - empieza));
		instance.shutdown();
		assertEquals(150000, colCustomers.size());
	}

	@Test
	public void testSearchHazelcast() {
		List<User> users = usersServices.findAll();
		Config cfg = new Config();
		cfg.setInstanceName("InstanceNode1");
		HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
		
		IMap<Long, User> usersMap = instance.getMap("usersMap");
		MapConfig userMapConfig = cfg.getMapConfig("usersMap");
		userMapConfig.setOptimizeQueries(true);
		usersMap.addIndex("id", true);
		usersMap.addIndex("name", true);
		usersMap.addIndex("phone", true);
		usersMap.addIndex("company", true);
		usersMap.addIndex("siret", true);
		for (User u : users) {
			usersMap.put(u.getId(), u);
		}
		EntryObject e = new PredicateBuilder().getEntryObject();
		Predicate predicate = (e.get("name").equal("A Aliquet Corp.").or(e.get("phone").equal("A Aliquet Corp.")).or(e.get("company").equal("A Aliquet Corp.")).or(e.get("siret").equal("A Aliquet Corp.")));
		Long empieza = System.currentTimeMillis();
		Collection<User> usuarios = usersMap.values(predicate);
		Long acaba = System.currentTimeMillis();
		System.out.println("Time expend:" + (acaba - empieza));
		if (usuarios != null && !usuarios.isEmpty()) {
			// Must to return 6 rows
			for ( User u : usuarios){
				System.out.println(u.getId());
			}
			instance.shutdown();
			assertEquals(6, usuarios.size());
		}
	}
	
	@Test
	public void testSort(){
		List<User> users = usersServices.findAll();
		List<User> userCollection = users.subList(0, 20);
		userCollection.sort(new UserComparator());
		assertNotNull(userCollection);
	}

}
