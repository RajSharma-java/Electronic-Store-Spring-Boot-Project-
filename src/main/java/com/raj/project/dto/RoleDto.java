package com.raj.project.dto;

import java.util.List;

import com.raj.project.entities.Role;
import com.raj.project.entities.User;

import lombok.Data;

@Data
public class RoleDto {

	private String roleId;
	
	private String name;
}
