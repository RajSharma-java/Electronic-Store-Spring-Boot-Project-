package com.raj.project.service;

import java.util.List;

import com.raj.project.dto.CreateOrderRequest;
import com.raj.project.dto.OrderDto;
import com.raj.project.dto.OrderUpdateRequest;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.entities.Cart;

public interface OrderService
{
	//create order
	OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders
    PageabaleResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir);

   OrderDto updateOrder(String orderId, OrderUpdateRequest request);

    //order methods(logic) related to order
}
