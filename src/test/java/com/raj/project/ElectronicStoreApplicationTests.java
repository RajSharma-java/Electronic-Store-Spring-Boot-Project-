package com.raj.project;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.raj.project.entities.User;
import com.raj.project.repository.UserRepository;
import com.raj.project.secuirty.JwtHelper;

@SpringBootTest
class ElectronicStoreApplicationTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtHelper jwtHelper;

//	@Test
//	void contextLoads() {
//	}

//	@Test
//	void testToken() {
//		User user=userRepository.findByEmail("durgesh@gmail.com").get();
//		
//		String jwtToken= jwtHelper.generateToken(user);
//		System.out.println(jwtToken);
//	}
}

