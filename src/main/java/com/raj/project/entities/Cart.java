package com.raj.project.entities;

import java.util.*;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
	@Id
	private String cartId;

	private Date createdDate;

	@OneToOne
	@JoinColumn(name = "userId")
	private User user;

	// mapping with cartItem

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

}
