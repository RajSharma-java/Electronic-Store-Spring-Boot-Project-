package com.raj.project.secuirty;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{

	// when unautorize people access our site
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
	AuthenticationException authException) throws IOException, ServletException 
	{
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		PrintWriter writer =response.getWriter();
		writer.print( authException.getMessage());
		
	}

	
}
