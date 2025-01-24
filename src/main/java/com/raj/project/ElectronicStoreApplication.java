package com.raj.project;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.raj.project.config.AppConstants;
import com.raj.project.entities.Role;
import com.raj.project.entities.User;
import com.raj.project.repository.RoleRepository;
import com.raj.project.repository.UserRepository;
import com.raj.project.secuirty.JwtHelper;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtHelper jetHelper;
	
	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}

	// when program is run automatic create the role it is hard code
	@Autowired
	private RoleRepository repository;

	@Override
	public void run(String... args) throws Exception {
		Role roleAdmin = repository.findByName("ROLE_"+AppConstants.ROLE_ADMIN).orElse(null);

		if (roleAdmin == null) {
			Role role1 = new Role();
			role1.setRoleId(UUID.randomUUID().toString());
			role1.setName("ROLE_"+AppConstants.ROLE_ADMIN);
			repository.save(role1);

		}

		Role roleNormal = repository.findByName("ROLE_"+AppConstants.ROLE_NORMAL).orElse(null);
		if (roleNormal == null) {
			Role role2 = new Role();
			role2.setRoleId(UUID.randomUUID().toString());
			role2.setName("ROLE_"+AppConstants.ROLE_NORMAL);
			repository.save(role2);

		}
		// ek admin role bna rhe hai
	User user=	userRepository.findByEmail("durgesh@gmail.com").orElse(null);
	  user = new User();
	 if (user == null) {
       
         user.setName("durgesh");
         user.setEmail("durgesh@gmail.com");
         user.setPassword(passwordEncoder.encode("durgesh"));
         user.setRoles(List.of(roleAdmin));
         user.setId(UUID.randomUUID().toString());

         userRepository.save(user);
	 }
	}

}
