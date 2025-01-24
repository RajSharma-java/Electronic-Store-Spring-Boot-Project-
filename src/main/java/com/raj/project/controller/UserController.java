package com.raj.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.UserDto;
import com.raj.project.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	


	// create user
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
		UserDto createUser = userService.createUser(userDto);
		return new ResponseEntity<>(createUser, HttpStatus.CREATED);
	}

	// update User
	@PutMapping("/update/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable String userId) {
		UserDto updatesUser = userService.updateUser(userDto, userId);
		return new ResponseEntity<>(updatesUser, HttpStatus.OK);

	}

	// delete user
	@DeleteMapping("{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable String userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<>("User Deleted successfully!!", HttpStatus.OK);
	}
//int pageNumber, int pageSize
	// getAllUser
	@GetMapping
	public ResponseEntity<PageabaleResponse<UserDto>> getAllUser(
			@RequestParam (value ="pageNumber", defaultValue = "0",required = false) int pageNumber,
			@RequestParam (value ="pageSize", defaultValue = "10",required = false) int pageSize,
			@RequestParam (value ="sortBy", defaultValue = "name",required = false) String sortBy,
			@RequestParam (value ="sortDir", defaultValue = "ASC",required = false) String sortDir
			
			) 
	{
		PageabaleResponse<UserDto> dto=userService.getAllUser(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);

	}
	
	// get All user by id 
	@GetMapping("{user_Id}")
	public ResponseEntity<UserDto> getAllUserById(@PathVariable String  user_Id){
		UserDto userDto=userService.getUserById(user_Id);
		return new ResponseEntity<>(userDto,HttpStatus.OK);
	}
	
	// get user by email
	@GetMapping("/email/{email_id}")
	public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email_id){
		UserDto getUser=userService.getUserByEmail(email_id);
		return new ResponseEntity<>(getUser,HttpStatus.OK);
		
	}
	@GetMapping("/key/{keyword}")
	public ResponseEntity<List<UserDto>> findUserByname(@PathVariable  String keyword){
		List<UserDto> savedUser=userService.searchUser(keyword);
		return new  ResponseEntity<>(savedUser,HttpStatus.OK);
		
	}
	

}
