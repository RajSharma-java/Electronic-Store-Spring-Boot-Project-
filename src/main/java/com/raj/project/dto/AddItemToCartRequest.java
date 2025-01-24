package com.raj.project.dto;

import java.util.Date;
import java.util.List;

import com.raj.project.entities.Cart;
import com.raj.project.entities.CartItem;
import com.raj.project.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddItemToCartRequest 
{

	private String  productId;
	private int quantity;
}
