package com.raj.project.service.imp;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.raj.project.dto.AddItemToCartRequest;
import com.raj.project.dto.CartDto;
import com.raj.project.entities.Cart;
import com.raj.project.entities.CartItem;
import com.raj.project.entities.Product;
import com.raj.project.entities.User;
import com.raj.project.exception.ResoursceNotFoundException;
import com.raj.project.repository.CartItemRepository;
import com.raj.project.repository.CartRepository;
import com.raj.project.repository.ProductRepository;
import com.raj.project.repository.UserRepository;
import com.raj.project.service.Cartservice;

@Service
public class CartServiceImp implements Cartservice
{
	@Autowired 
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	
	@Override
	public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
		String productId=request.getProductId();
		int quantity=request.getQuantity();
		 if (quantity <= 0) {
	            throw new ResoursceNotFoundException("Requested quantity is not valid !!");
	        }
		// fetch the product from database 
		Product product=productRepository.findById(productId).orElseThrow(()-> new ResoursceNotFoundException("Product not found!!"));
		
		// fetch the user 
		User user=userRepository.findById(userId).orElseThrow(()-> new ResoursceNotFoundException("user not found!! check again"));
		
		// cart the fetch
		Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedDate(new Date());
        }
		
        //perform cart operations
        //if cart items already present; then update
        AtomicReference<Boolean> updated = new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {

            if (item.getProduct().getId().equals(productId)) {
                //item already present in cart
            	
            	
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

//        cart.setItems(updatedItems);

        //create items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem); // add data in cart
        }

        // cart is new 
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
	}

	@Override
	public void removeItemFromCart(String userId, int cartItem) {
		  //conditions
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResoursceNotFoundException("Cart Item not found !!"));
        cartItemRepository.delete(cartItem1);

	}

	@Override
	public void clearCart(String userId) {
		 //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResoursceNotFoundException("user not found in database!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResoursceNotFoundException("Cart of given user not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
		
	}

	@Override
	public CartDto getCartByUser(String userId) {
		  User user = userRepository.findById(userId).orElseThrow(() -> new ResoursceNotFoundException("user not found in database!!"));
	        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResoursceNotFoundException("Cart of given user not found !!"));

	        return mapper.map(cart, CartDto.class);
	}

}
