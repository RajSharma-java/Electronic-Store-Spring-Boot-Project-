package com.raj.project.controller;

import java.util.List;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.raj.project.dto.CategoryDto;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.ProductDto;
import com.raj.project.entities.Product;
import com.raj.project.service.CategoryService;
import com.raj.project.service.ProductService;

@RestController
@RequestMapping("/category")
public class CategoryController
{
	@Autowired
	private CategoryService service;
	
	@Autowired
	private ProductService productService;
	
	// create category 
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto ) {
		// call the method of service class
		CategoryDto create= service.createCategory(categoryDto);
		return new ResponseEntity<>(create,HttpStatus.CREATED);
		
	}
	// get All category
	@GetMapping
	public ResponseEntity<PageabaleResponse<CategoryDto>> getAllCategory(
			@RequestParam (value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam (value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam (value="sortDir", defaultValue = "asc", required = false) String sortDir
			){
		//call the method of service
		PageabaleResponse<CategoryDto> categoryDto =service.getAll(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
	}
	
	// get  category by  id
	@GetMapping("{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable String categoryId)
	{
		CategoryDto category=service.getcategoryById(categoryId);
		return new ResponseEntity<>(category,HttpStatus.OK);
	}
	
	// update category
	@PutMapping("{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(
			@RequestBody CategoryDto categoryDto,
			@PathVariable String categoryId){
		
		CategoryDto update=service.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(update,HttpStatus.OK);
		
	}
	
	// delete mapping
	@DeleteMapping("{categoryId}")
	public ResponseEntity<String> deleteCateory(@PathVariable String categoryId){
		service.deleteCategory(categoryId);
		return new ResponseEntity<>("category  deletd successfully", HttpStatus.OK);
	
	}
	// create product with according 
	// here we write code becuase we does not want to change  url
	@PostMapping("/{categoryId}/product")
	public ResponseEntity<ProductDto> createProductWithCategory(
			@PathVariable("categoryId") String categoryId,
			@RequestBody ProductDto productDto)
			
	{
		ProductDto createProduct=productService.createWithCategory(productDto, categoryId);
		return new ResponseEntity<>(createProduct,HttpStatus.CREATED);
		
	}
			
	// update product With category
	@PutMapping("/{categoryId}/product/{productId}")
	public ResponseEntity<ProductDto> updateProductWithCategory(
			@PathVariable String categoryId,
			@PathVariable String  productId
			
			
			)
	{
		ProductDto dto=productService.updateCategory(productId, categoryId);
	return new ResponseEntity<>(dto,HttpStatus.OK);
	
		
	}
	
	
	// get All products by category
	
	@GetMapping("/{categoryId}/product")
	public ResponseEntity<PageabaleResponse<ProductDto>> getAllProducts(
			@PathVariable String categoryId,
		
			@RequestParam (value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam (value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam (value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam (value="sortDir", defaultValue = "asc", required = false) String sortDir
			
			
			)
	{
		PageabaleResponse<ProductDto> dto=productService.getAllOfCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
	 return new ResponseEntity<>(dto,HttpStatus.OK);
	

	
		
	}
}
