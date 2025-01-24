package com.raj.project.service;

import java.util.List;

import com.raj.project.dto.CategoryDto;
import com.raj.project.dto.PageabaleResponse;

public interface CategoryService 
{
	
	// create Category
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update category
	CategoryDto updateCategory(CategoryDto CategoryDto,String categoryId);
	
	// delete category
	void deleteCategory(String id);
	
	// get all category
	PageabaleResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get single category
	CategoryDto  getcategoryById(String categoryId);
	
	// get category by keyword name 
	CategoryDto getCategoryByName(String keyword);
	
}
