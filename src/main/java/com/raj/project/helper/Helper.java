package com.raj.project.helper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.raj.project.dto.PageabaleResponse;
import com.raj.project.dto.UserDto;
import com.raj.project.entities.User;

public class Helper {
	
	public static <U,V> PageabaleResponse<V> getPageableResponse(Page<U> page,Class<V> type){
		// U is entity and V is dto 
		
		List<U> entity = page.getContent();
		// now convert that data into dto
		List<V> dtoList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
		
		PageabaleResponse<V> pageabaleResponse= new PageabaleResponse<>();
		pageabaleResponse.setContent(dtoList);
		pageabaleResponse.setPageNumber(page.getNumber()+1);
		pageabaleResponse.setPageSize(page.getSize());
		pageabaleResponse.setTotalElements(page.getTotalElements());
		pageabaleResponse.setTotalPage(page.getTotalPages());
		pageabaleResponse.setLastPage(page.isLast());
		return pageabaleResponse;
		
	}

}
