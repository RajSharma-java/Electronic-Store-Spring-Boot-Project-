package com.raj.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{

}
