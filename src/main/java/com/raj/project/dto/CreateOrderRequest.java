package com.raj.project.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest 
{
	 @NotBlank(message = "Cart id is required !!")
	    private String cartId;

	    @NotBlank(message = "Cart id is required !!")
	    private String userId;


	    private String orderStatus = "PENDING";
	    private String paymentStatus = "NOTPAID";
	    @NotBlank(message = "Address is required !!")
	    private String billingAddress;
	    @NotBlank(message = "Phone number is required !!")
	    private String billingPhone;
	    @NotBlank(message = "Billing name  is required !!")
	    private String billingName;

	   

}
