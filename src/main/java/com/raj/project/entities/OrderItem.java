package com.raj.project.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "order_items")
public class OrderItem 
{
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private  int orderItemId;

	    private  int quantity;

	    private  int totalPrice;

	    @OneToOne
	    @JoinColumn(name = "product_id")
	    private  Product product;

	    @ManyToOne
	    @JoinColumn(name = "order_id")
	    private  Order order;
}
