package com.raj.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.project.entities.Order;
import com.raj.project.entities.OrderItem;
import com.raj.project.entities.User;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer>
{
	
}
