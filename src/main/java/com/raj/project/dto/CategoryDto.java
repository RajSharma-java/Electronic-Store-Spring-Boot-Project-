package com.raj.project.dto;

import com.raj.project.entities.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto 
{
	private String id;
	
	private String title;
	private String description;
	private String coverImage;

}
