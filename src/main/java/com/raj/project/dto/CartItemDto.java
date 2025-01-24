package com.raj.project.dto;

import java.util.Date;
import java.util.List;

import com.raj.project.entities.Cart;
import com.raj.project.entities.CartItem;
import com.raj.project.entities.User;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
class CartItemDto {

	private int cartItemId;

	private ProductDto product;

	private int quantity;

	private int totalPrice;

}
