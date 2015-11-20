package com.innovation.persistence.dao;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

/**
 * Clase DAO de Usuarios
 * @since Innovation Technical Test
 * @author sheinx
 *
 */
@Entity
@Table(name="D_USER")
public class User implements Serializable{

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
	
	
}
