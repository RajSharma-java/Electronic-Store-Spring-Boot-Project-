package com.raj.project.dto;

import java.sql.Date;
import java.util.Locale.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto 
{
	private String id;
	private String title;
	private String description;
	private int price;
	 private int discountPrice;
	private int quantity;
	private Date addedDate;
	private boolean live;
	private boolean Stock;
	
	
	private CategoryDto category;

}
