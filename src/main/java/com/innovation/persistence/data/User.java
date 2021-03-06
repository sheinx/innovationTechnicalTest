package com.innovation.persistence.data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Clase DAO de Usuarios
 * @since Innovation Technical Test
 * @author sheinx
 *
 */
@Entity
@Table(name="D_USER")
public class User implements Serializable,Comparable<User>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2569497930343747490L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
 
	private String name;
 
	private String phone;
	
	private String company;
	
	private String siret;

	public Long getId() {
		return id;
	}
	
	public User(){
		
	}
	
	public User(String name, String phone, String company, String siret){
		this.setName(name);
		this.setCompany(company);
		this.setPhone(phone);
		this.setSiret(siret);
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}
	
	@Override
	public int compareTo(User u) {
	  int lastCmp = this.name.compareTo(u.getName());
	  return (lastCmp != 0 ? lastCmp : siret.compareTo(u.getSiret()));
	}
	
	@Override 
	public boolean equals(Object other) {
	    boolean result = false;
	    if (other instanceof User) {
	        User that = (User) other;
	        result = (this.id == that.getId());
	    }
	    return result;
	}
	
}
