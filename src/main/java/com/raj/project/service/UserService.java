package com.raj.project.service;

import java.util.List;

import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.UserDto;

public interface UserService 
{
	// create User
	UserDto createUser(UserDto userDto);
	
	// update user
	UserDto updateUser(UserDto userDto, String user_id);

	//delete user
	void deleteUser(String user_id);
	
	// get All user
	PageabaleResponse<UserDto> getAllUser(int pageNumber, int pageSize,String sortBy, String sortDir );
	
	// get user by id
	UserDto getUserById( String user_id);
	
	// get user by email
	UserDto getUserByEmail(String user_email);
	
	// search user
	List<UserDto> searchUser(String keyword);

	
	
	
	// other method
	
}
