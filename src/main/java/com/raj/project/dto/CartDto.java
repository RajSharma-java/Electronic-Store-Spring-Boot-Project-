package com.raj.project.dto;

import java.util.*;

import com.raj.project.entities.Cart;
import com.raj.project.entities.CartItem;
import com.raj.project.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
	private String cartId;

	private Date createdDate;

	private List<CartItemDto> items = new ArrayList<>();

}
