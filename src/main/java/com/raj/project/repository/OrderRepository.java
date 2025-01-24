package com.raj.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Order;
import com.raj.project.entities.User;

public interface OrderRepository extends JpaRepository<Order, String>
{
//  fetch the user 
	
	List<Order> findByUser(User user);

}
