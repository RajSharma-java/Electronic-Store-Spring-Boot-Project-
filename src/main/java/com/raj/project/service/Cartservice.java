package com.raj.project.service;

import com.raj.project.dto.AddItemToCartRequest;
import com.raj.project.dto.CartDto;

public interface Cartservice 
{
	// add items to cart
	// case 1: cart for user is not available then we create the cart: 
	// case 2: cart is available then we add items to cart
	
	CartDto addItemToCart(String userId, AddItemToCartRequest request);
	
	// remove item from cart
	void removeItemFromCart(String userId, int cartItem);
	
	// void clear cart
	void clearCart(String userId);

	CartDto getCartByUser(String userId);
}
