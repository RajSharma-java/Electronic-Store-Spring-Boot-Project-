package com.raj.project.service;

import java.util.List;


import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.ProductDto;




public interface ProductService 
{
	//create product
	ProductDto createProduct(ProductDto productDto);
	
	// update product
	ProductDto updateProduct(ProductDto productDto, String productId);

	// delete product
	void deleteProduct(String productId);

	// getAll product
	PageabaleResponse <ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get product by id
	ProductDto getProductById(String productId);
	
	// find product by name
	PageabaleResponse<ProductDto> getProductByTitle(String keyword,int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// find product whose is live
	PageabaleResponse<ProductDto> getProductLive(int pageNumber, int pageSize, String sortBy, String sortDir);
	
	// get product by category 
	 //create product with category
    ProductDto createWithCategory(ProductDto productDto,String categoryId);


    //update category of product
    ProductDto updateCategory(String productId,String categoryId);

    PageabaleResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy, String sortDir);
	
}
