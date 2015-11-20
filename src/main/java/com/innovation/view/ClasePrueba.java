package com.innovation.view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;

import com.innovation.persistence.dao.User;
import com.innovation.persistence.repository.UserRepository;

@Configuration
@EnableAutoConfiguration
public class ClasePrueba {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ClasePrueba.class);
		UserRepository repository = context.getBean(UserRepository.class);

		// Guardar un conjunto de usuarios
		repository.save(new User("Juan", "656", "Atos", "IbanES32"));

		// findAll heredado de la interface CrudRepository
		Iterable<User> todos = repository.findAll();
		System.out.println("Listar todos los Usuarios:");
		for (User usr : todos) {
			System.out.println("\t" + usr);
		}

		context.close();
	}

}
