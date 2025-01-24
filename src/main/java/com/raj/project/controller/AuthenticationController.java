package com.raj.project.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

//import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.raj.project.dto.JWTRequest;
import com.raj.project.dto.JWTResponse;
import com.raj.project.dto.UserDto;
import com.raj.project.entities.User;
import com.raj.project.secuirty.JwtHelper;

@RestController
@RequestMapping("/auth")
public class AuthenticationController 
{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private ModelMapper mapper;
	
	
	private Logger logger= LoggerFactory.getLogger(AuthenticationController.class);
	
	// method to generate token
		@PostMapping("/generate-token")
	public ResponseEntity <JWTResponse> login(@RequestBody JWTRequest request){
			logger.info("Username {}, Password {}", request.getEmail(),request.getPassword());
			
			// now authenticate username and password
			
			this.doAuthenticate(request.getEmail(),request.getPassword());
			
			User user=(User) userDetailsService.loadUserByUsername(request.getEmail());
			
			// generate token
			String token=jwtHelper.generateToken(user);
			JWTResponse response=JWTResponse.builder().token(token).user(mapper.map(user, UserDto.class)).build();
			return ResponseEntity.ok(response);
		
		
	}

		 private void doAuthenticate(String email, String password) {

		        try {
		            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
		            authenticationManager.authenticate(authentication);

		        } catch (BadCredentialsException ex) {
		            throw new BadCredentialsException("Invalid Username and Password !!");
		        }
		
		
	}


}
