package com.raj.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, String>
{
	

}
