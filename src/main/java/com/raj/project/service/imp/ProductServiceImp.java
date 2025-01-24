package com.raj.project.service.imp;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.ProductDto;
import com.raj.project.entities.Category;
import com.raj.project.entities.Product;
import com.raj.project.exception.ResoursceNotFoundException;
import com.raj.project.helper.Helper;
import com.raj.project.repository.CategoryRepository;
import com.raj.project.repository.ProductRepository;
import com.raj.project.service.ProductService;

@Service
public class ProductServiceImp implements ProductService {
	@Autowired
	private ProductRepository repository;

	@Autowired
	private ModelMapper mapper;

	
	@Autowired
	private CategoryRepository categoryRepository;
	Random random = new Random();

	// create product
	@Override
	public ProductDto createProduct(ProductDto productDto) {
		// set Autogenreated id
		String id = UUID.randomUUID().toString();
		productDto.setId(id);

		// whenever you create method firstly you convert dto to entity
		Product product = mapper.map(productDto, Product.class);
		// added date
		product.setAddedDate(new Date());
		Product savedProduct = repository.save(product);
		return mapper.map(savedProduct, ProductDto.class);

	}

	// update product

	@Override
	public ProductDto updateProduct(ProductDto productDto, String productId) {
		// firslty find the id is present or not in database
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ResoursceNotFoundException("Given id not found!!"));
		// now you find id then update value
		product.setTitle(productDto.getTitle());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setQuantity(productDto.getQuantity());
		product.setDiscountPrice(productDto.getDiscountPrice());
		product.setLive(productDto.isLive());
		product.setStock(productDto.isStock());

		ProductDto dto = mapper.map(product, ProductDto.class);

		return dto;
	}

	// delete product
	@Override
	public void deleteProduct(String productId) {
		// find the id is available or not in database
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ResoursceNotFoundException("Id not found by given id"));
		repository.delete(product);

	}

	// getAll product
	@Override
	public PageabaleResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// sorting
		Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
		// pageable
		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
		Page<Product> product = repository.findAll(pageable);
		PageabaleResponse response = Helper.getPageableResponse(product, ProductDto.class);
		return response;

	}

	// get product by id
	@Override
	public ProductDto getProductById(String productId) {
		// find id firstly
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ResoursceNotFoundException("id not found"));

		// chnage in dto
		ProductDto dto = mapper.map(product, ProductDto.class);

		return dto;
	}

    @Override
    public PageabaleResponse<ProductDto> getProductLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = repository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageabaleResponse<ProductDto> getProductByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = repository.findByTitleContaining(subTitle, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    // create product with category
	@Override
	public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new ResoursceNotFoundException("Given id not Found!!"));
		
		String id = UUID.randomUUID().toString();
		productDto.setId(id);

		// whenever you create method firstly you convert dto to entity
		Product product = mapper.map(productDto, Product.class);
		// added date
		product.setAddedDate(new Date());
		product.setCategory(category);
		Product savedProduct = repository.save(product);
		return mapper.map(savedProduct, ProductDto.class);
		
	
	}

	// update product according to category
	@Override
	public ProductDto updateCategory(String productId, String categoryId) {
		Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new ResoursceNotFoundException("Given id not Found!!"));
		Product product = repository.findById(productId)
				.orElseThrow(() -> new ResoursceNotFoundException("Id not found !!!"));
		product.setCategory(category);
		Product savedProduct=repository.save(product);

		ProductDto dto = mapper.map(product, ProductDto.class);

		return dto;
	}
	// getAllProduct product according to category

	@Override
	public PageabaleResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize,
			String sortBy, String sortDir) 
	{
		Category category=categoryRepository.findById(categoryId).orElseThrow(() -> new ResoursceNotFoundException("Given id not Found!!"));
		   Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
	        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Product> page=repository.findByCategory(category, pageable);
	        return Helper.getPageableResponse(page, ProductDto.class);
		
	}

}
