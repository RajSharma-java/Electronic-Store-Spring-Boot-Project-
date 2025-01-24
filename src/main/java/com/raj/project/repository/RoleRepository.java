package com.raj.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
	
	Optional<Role> findByName(String name);

}
