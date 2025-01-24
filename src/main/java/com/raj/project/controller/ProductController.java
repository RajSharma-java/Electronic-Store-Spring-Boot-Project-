package com.raj.project.controller;



import java.util.List;

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

import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.ProductDto;
import com.raj.project.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductController

{
	@Autowired
	private ProductService service;

	// create product
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto)
	{
		ProductDto dto = service.createProduct(productDto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	// update product
	@PutMapping("{productId}")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto, 
			@PathVariable String productId)
	{
	ProductDto updateProduct=	service.updateProduct(productDto, productId);
		return new ResponseEntity<>(updateProduct,HttpStatus.OK);
	}
	
	//delete product
	@DeleteMapping("{productId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String productId ) 
	{
		service.deleteProduct(productId);
		return new ResponseEntity<>("product deleted successfully",HttpStatus.OK);
	}

	//getAll Product
	@GetMapping
	public ResponseEntity<PageabaleResponse<ProductDto>> getAll(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
			
			) 
	{
		
		PageabaleResponse<ProductDto> dto=service.getAllProduct(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	
	}
	
	// get Product by id
	@GetMapping("{productId}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable String productId){
		ProductDto dto=service.getProductById(productId);
		return  new ResponseEntity<>(dto,HttpStatus.OK);
	}
	
	@GetMapping("/live")
	public ResponseEntity<PageabaleResponse<ProductDto>> getAllLive(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
			
			) 
	{
		
		PageabaleResponse<ProductDto> dto=service.getProductLive(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	
	}

	@GetMapping("/key/{keyword}")
	public ResponseEntity<PageabaleResponse<ProductDto>> getAllByTitle( @PathVariable  String keyword,
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
			@RequestParam(value="sortBy", defaultValue = "title", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required = false) String sortDir
			
			) 
	{
		
		PageabaleResponse<ProductDto> dto=service.getProductByTitle(keyword,pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<>(dto, HttpStatus.OK);
	
	}
	
	
	
	
}
