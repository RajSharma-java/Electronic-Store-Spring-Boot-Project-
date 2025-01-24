package com.raj.project.service.imp;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.raj.project.config.AppConstants;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.UserDto;
import com.raj.project.entities.Role;
import com.raj.project.entities.User;
import com.raj.project.exception.ResoursceNotFoundException;
import com.raj.project.helper.Helper;
import com.raj.project.repository.RoleRepository;
import com.raj.project.repository.UserRepository;
import com.raj.project.service.UserService;

@Service
public class UserServiceImp implements UserService {
// in the service layer we write all the business logic 

	@Autowired
	private UserRepository repository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;
	Random random = new Random();

	@Override
	public UserDto createUser(UserDto userDto) {
		// set Automatic id creation

		String id = UUID.randomUUID().toString();
		userDto.setId(id);
		
	
		// now change dto to entity

		User user = mapper.map(userDto, User.class);
		
		
		//user password encoder
		user.setPassword(passwordEncoder.encode(user.getPassword()));
			
	
		
		//assign normal role of user by default jo bhi api se user bnagea usko humlog normal user banyenge 
		Role role= new Role();
		role.setRoleId(UUID.randomUUID().toString());
		role.setName("ROLE_"+AppConstants.ROLE_NORMAL);
		Role roleNormal=roleRepository.findByName("ROLE_"+AppConstants.ROLE_NORMAL).orElse(role);
		user.setRoles(List.of(roleNormal));
		
		
		
		
		// now save the data in databse
		User savedUser = repository.save(user);
		// now convert entity to dto
		UserDto createUser = mapper.map(savedUser, UserDto.class);

		return createUser;
	}

	// update User

	@Override
	public UserDto updateUser(UserDto userDto, String user_id) {
		// TODO Auto-generated method stub

		User user = repository.findById(user_id)
				.orElseThrow(() -> new ResoursceNotFoundException("User not find by given id"));
		user.setName(userDto.getName());
		user.setGender(userDto.getGender());
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		user.setAbout(userDto.getAbout());
		User updateUser = repository.save(user);
		// now change entity dto
		UserDto updated = mapper.map(updateUser, UserDto.class);

		return updated;
	}

	// delete user
	@Override
	public void deleteUser(String user_id) {
		// find the use
		User user = repository.findById(user_id)
				.orElseThrow(() -> new ResoursceNotFoundException("User not find by given id"));
		repository.delete(user);
	}

	// get All user
	@Override
	public PageabaleResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {

		// for shorting
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		// for pageable
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<User> page = repository.findAll(pageable);
		PageabaleResponse pageabaleResponse= Helper.getPageableResponse(page, UserDto.class);
		return pageabaleResponse;
	}
	
	// get a single uses by id
	@Override
	public UserDto getUserById(String user_id) {
		// find the user is present or not in database
		User user = repository.findById(user_id)
				.orElseThrow(() -> new ResoursceNotFoundException("User not find by given id"));
		return mapper.map(user, UserDto.class);
	}

	@Override
	public UserDto getUserByEmail(String user_email) {
		// find the email
		User user = repository.findByEmail(user_email)
				.orElseThrow(() -> new ResoursceNotFoundException("User not find by given email"));
		;
		return mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> searchUser(String keyword) {
		List<User> users = repository.findByNameContaining(keyword);
		List<UserDto> dtoList = users.stream().map(user -> mapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		return dtoList;
	}

}
