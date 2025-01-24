package com.raj.project.dto;

import java.util.List;

import com.raj.project.entities.Role;

import jakarta.persistence.Column;
import lombok.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto 
{
private String id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String gender;
	
	private String about;
	
	private List<RoleDto> roles;
	
}
