package com.raj.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.User;

public interface UserRepository extends JpaRepository<User, String>
{
	Optional<User>
	findByEmail(String email);
	
	List<User> findByNameContaining(String keyword);
}
