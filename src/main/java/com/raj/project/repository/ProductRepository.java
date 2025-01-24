package com.raj.project.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Category;
import com.raj.project.entities.Product;

public interface ProductRepository extends JpaRepository<Product, String> 
{
	  Page<Product> findByTitleContaining(String subTitle, Pageable pageable);

	    Page<Product> findByLiveTrue(Pageable pageable);

	    Page<Product> findByCategory(Category category,Pageable pageable);
	
}
