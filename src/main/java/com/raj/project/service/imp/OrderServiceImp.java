package com.raj.project.service.imp;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raj.project.dto.CreateOrderRequest;
import com.raj.project.dto.OrderDto;
import com.raj.project.dto.OrderUpdateRequest;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.entities.Cart;
import com.raj.project.entities.CartItem;
import com.raj.project.entities.Order;
import com.raj.project.entities.OrderItem;
import com.raj.project.entities.User;
import com.raj.project.exception.ResoursceNotFoundException;
import com.raj.project.helper.Helper;
import com.raj.project.repository.CartRepository;
import com.raj.project.repository.OrderRepository;
import com.raj.project.repository.UserRepository;
import com.raj.project.service.OrderService;

@Service
public class OrderServiceImp implements OrderService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartRepository cartRepository;

	@Override
	public OrderDto createOrder(CreateOrderRequest orderDto) {
		String userId=orderDto.getUserId();
		String cartId=orderDto.getCartId();
		// fetch user
		User user=userRepository.findById(userId).orElseThrow(()-> new ResoursceNotFoundException("User id not found"));
		
		// fetch cart 
		Cart cart=cartRepository.findById(cartId).orElseThrow(() -> new ResoursceNotFoundException("cart id not found"));
		
		List<CartItem> cartItems=cart.getItems();

        if (cartItems.size() <= 0) {
            throw new ResoursceNotFoundException("Invalid number of items in cart !!");

        }

        //other checks validation

        // find order 
        Order order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingPhone(orderDto.getBillingPhone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

//      For set  orderItems,amount

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);
        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {
//            CartItem->OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());


        order.setOrderItems(orderItems);
        order.setOrderAmount(orderAmount.get());

        System.out.println(order.getOrderItems().size());

   
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return mapper.map(savedOrder, OrderDto.class);
		
		
	}
	// remove order
	@Override
	public void removeOrder(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResoursceNotFoundException("order is not found !!"));
        orderRepository.delete(order);

	}
	// get order of user

	@Override
	public List<OrderDto> getOrdersOfUser(String userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new ResoursceNotFoundException("User not found !!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
        
        return orderDtos;
	}
	
// get Order
	@Override
	public PageabaleResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {
		  Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
	        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
	        Page<Order> page = orderRepository.findAll(pageable);
	        return Helper.getPageableResponse(page, OrderDto.class);
	}
	@Override
	public OrderDto updateOrder(String orderId, OrderUpdateRequest request) {
		   //get the order
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResoursceNotFoundException("Invalid update data"));
        order.setBillingName(request.getBillingName());
        order.setBillingPhone(request.getBillingPhone());
        order.setBillingAddress(request.getBillingAddress());
        order.setPaymentStatus(request.getPaymentStatus());
        order.setOrderStatus(request.getOrderStatus());
        order.setDeliveredDate(request.getDeliveredDate());
        Order updatedOrder = orderRepository.save(order);
        return mapper.map(updatedOrder, OrderDto.class);
	}

}
