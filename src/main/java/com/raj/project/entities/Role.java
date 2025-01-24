package com.raj.project.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Entity
@Data
public class Role 
{
		@Id
		private String roleId;
		private String name;
		//Admin and normal
		@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	    private List<User> users = new ArrayList<>();
}
