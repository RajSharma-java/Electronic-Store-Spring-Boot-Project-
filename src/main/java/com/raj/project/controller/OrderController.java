package com.raj.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.project.config.AppConstants;
import com.raj.project.dto.CreateOrderRequest;
import com.raj.project.dto.OrderDto;
import com.raj.project.dto.OrderUpdateRequest;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private OrderService orderService;

	// create

	@PreAuthorize("hasAnyRole('"+AppConstants.ROLE_ADMIN+"','"+AppConstants.ROLE_NORMAL+"')") // method based url security
	@PostMapping
	public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrderRequest request) {
		OrderDto order = orderService.createOrder(request);
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
	@DeleteMapping("/{orderId}")
	public ResponseEntity<String> removeOrder(@PathVariable String orderId) {
		orderService.removeOrder(orderId);

		return new ResponseEntity<>("Order Removed", HttpStatus.OK);

	}

	// get orders of the user
	//get orders of the user
    //normal user
	@PreAuthorize("hasAnyRole('"+AppConstants.ROLE_ADMIN+"','"+AppConstants.ROLE_NORMAL+"')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable String userId) {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser, HttpStatus.OK);
    }

	@PreAuthorize("hasRole('"+AppConstants.ROLE_ADMIN+"')")
	@GetMapping
	public ResponseEntity<PageabaleResponse<OrderDto>> getOrders(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = "orderedDate", required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) {
		PageabaleResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(orders, HttpStatus.OK);
	}

	// Assignment Solution: update order
	@PutMapping("/{orderId}")
	public ResponseEntity<OrderDto> updateOrder(@PathVariable("orderId") String orderId,
			@RequestBody OrderUpdateRequest request) {

		OrderDto dto = this.orderService.updateOrder(orderId, request);
		return ResponseEntity.ok(dto);

	}

}
