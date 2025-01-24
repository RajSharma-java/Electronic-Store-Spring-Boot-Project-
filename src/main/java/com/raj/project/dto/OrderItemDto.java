package com.raj.project.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderItemDto {
	private int orderItemId;

    private int quantity;

    private int totalPrice;

    private ProductDto product;
}
