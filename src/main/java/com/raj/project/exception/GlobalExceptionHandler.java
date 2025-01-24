package com.raj.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public final class GlobalExceptionHandler {
	
	// handle resource not found exception
	@ExceptionHandler(ResoursceNotFoundException.class)
	public ResponseEntity<String> resourceNotFoundExceptionHandler(ResoursceNotFoundException e){
		return new ResponseEntity<String>("Resource Not Found",HttpStatus.NOT_FOUND);
		
	}

}
