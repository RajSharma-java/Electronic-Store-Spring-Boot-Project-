package com.raj.project.service.imp;

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

import com.raj.project.dto.CategoryDto;
import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.UserDto;
import com.raj.project.entities.Category;
import com.raj.project.exception.ResoursceNotFoundException;
import com.raj.project.helper.Helper;
import com.raj.project.repository.CategoryRepository;
import com.raj.project.service.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
	private CategoryRepository repository;

	@Autowired
	private ModelMapper mapper;

	Random random = new Random();

	// Create Category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// now generate Auto id
		String id = UUID.randomUUID().toString();
		categoryDto.setId(id);
		// firstly change dto to entity
		Category category = mapper.map(categoryDto, Category.class);
		// now save the data in the database
		Category savedCategory = repository.save(category);

		// now change the entity to dto
		return mapper.map(savedCategory, CategoryDto.class);
	}

	// update category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, String categoryId) {
		// firstly find the category is available or not in the database
		Category category = repository.findById(categoryId)
				.orElseThrow(() -> new ResoursceNotFoundException("Category Not Found!!"));
		category.setDescription(categoryDto.getDescription());
		category.setTitle(categoryDto.getTitle());
		// now save the data
		Category saved = repository.save(category);
		return mapper.map(saved, CategoryDto.class);
	}

	@Override
	public void deleteCategory(String id) {
		Category deleted = repository.findById(id).orElseThrow(() -> new ResoursceNotFoundException("id not founf!!"));
		repository.delete(deleted);

	}

	// getAll Category
	@Override
	public PageabaleResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
		// pageable and shorting code
		
		// sorting 
		Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
		//pageable
		Pageable pageable=PageRequest.of(pageNumber-1, pageSize,sort);
		// get All category form database
		Page<Category>  page= repository.findAll(pageable);
		PageabaleResponse response=Helper.getPageableResponse(page, CategoryDto.class);

		
		// now change the category into dto
	
		return response;
	}

	// get Category by id
	@Override
	public CategoryDto getcategoryById(String categoryId) {
		// check that id is presnt or not in database
		Category category = repository.findById(categoryId)
				.orElseThrow(() -> new ResoursceNotFoundException("Id not found, Check id again"));

		// now convert into dto

		return mapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryByName(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

}
