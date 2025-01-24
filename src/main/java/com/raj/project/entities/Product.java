package com.raj.project.entities;



import java.util.Date;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product 
{
	@Id
	@Column(name="productId")
	private String id;
	private String title;
	private String description;
	private int price;
	 private int discountPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	
	private boolean stock;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_Id")
	private Category category;
	
	
}
