package com.raj.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.project.dto.AddItemToCartRequest;
import com.raj.project.dto.CartDto;
import com.raj.project.service.Cartservice;
import com.raj.project.service.imp.CartServiceImp;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private Cartservice cartService;

	// create cart
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@PostMapping("{userId}")
	public ResponseEntity<CartDto> addItemToCart(@PathVariable String userId,
			@RequestBody AddItemToCartRequest request) {

		CartDto cartDto = cartService.addItemToCart(userId, request);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@DeleteMapping("/{userId}/items/{itemId}")
	public ResponseEntity<String> removeItemFromCart(@PathVariable String userId, @PathVariable int itemId) {
		cartService.removeItemFromCart(userId, itemId);

		return new ResponseEntity<>("Cart Deleted Successfully", HttpStatus.OK);

	}

	// clear cart
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> clearCart(@PathVariable String userId) {
		cartService.clearCart(userId);

		return new ResponseEntity<>("Now cart is blank", HttpStatus.OK);

	}

	// add items to cart
	@PreAuthorize("hasAnyRole('NORMAL','ADMIN')")
	@GetMapping("/{userId}")
	public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
		CartDto cartDto = cartService.getCartByUser(userId);
		return new ResponseEntity<>(cartDto, HttpStatus.OK);
	}

}
