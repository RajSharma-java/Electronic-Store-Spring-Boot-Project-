package com.raj.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Cart;
import com.raj.project.entities.Product;
import com.raj.project.entities.User;

public interface CartRepository extends JpaRepository<Cart, String>
{
	   Optional<Cart> findByUser(User user);

//	Optional<Product> findByUser(User user);
}
