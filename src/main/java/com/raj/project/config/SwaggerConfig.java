package com.raj.project.config;

import java.util.ArrayDeque;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration

public class SwaggerConfig
{
	
	
	@Bean
	public Docket docket() {
		
		Docket docket= new Docket(DocumentationType.SWAGGER_2);
		docket.apiInfo(getApiInfo());
		return docket;
		
	}

	private ApiInfo getApiInfo() {
		ApiInfo apiInfo= new ApiInfo("Electronic Store backend : APIS", 
				"This is backend project created by Raj Sharma",
				"1.0.0V",
				"https://www.raj-java-developer.com",
				new Contact("raj sharma","https://www.instagram.com/rajsharma_772","rajsahrma2278608@gmail.com"), 
				"License of APIS", 
				"https://www.rajsahrma.com/about",
				new ArrayDeque<>());
		return null;
	}

}
