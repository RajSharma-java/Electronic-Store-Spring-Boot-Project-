package com.raj.project.exception;


import lombok.Builder;


@Builder
public class ResoursceNotFoundException extends RuntimeException {
	
	public ResoursceNotFoundException() {
		super("Resource not found!! ");
	}
	
	public ResoursceNotFoundException(String message) {
		super(message);
	}
	

}
