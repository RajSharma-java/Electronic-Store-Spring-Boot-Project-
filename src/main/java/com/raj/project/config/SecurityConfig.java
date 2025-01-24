package com.raj.project.config;

import java.beans.BeanProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.raj.project.controller.AuthenticationController;
import com.raj.project.secuirty.JWTAuthenticationFilter;
import com.raj.project.secuirty.JwtAuthenticationEntryPoint;

@Configuration
//@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig 
{
	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;
	
	@Autowired
	private JWTAuthenticationFilter filter;
	
	// securityfilterChain beans
	
	final  String [] PUBLIC_URLS = {
		"/swagger-ui/**",
		"/webjars/**",
		"/swagger-resources/**"
	};
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception
	{
		// configuration 
		 
		//urls
		// public and private
		// urls for admin, urls for user
		security.cors(httpSecurityCorsConfigure -> httpSecurityCorsConfigure.disable());
		
		security.csrf(httpSecurityCsrfConfigure -> httpSecurityCsrfConfigure.disable());
		
		// that is url configuration --> yeh code yeh bta rha hai ki koun kya kya access kr skta hai
		security.authorizeHttpRequests(request->
				request.requestMatchers(HttpMethod.GET,"/user").permitAll()
				.requestMatchers(HttpMethod.DELETE,"/user/**").hasRole(AppConstants.ROLE_ADMIN)                       
				.requestMatchers("/product/**").hasRole(AppConstants.ROLE_ADMIN)
				.requestMatchers(HttpMethod.POST,"/user").permitAll()
				.requestMatchers(PUBLIC_URLS).permitAll()
				.requestMatchers(HttpMethod.PUT,"/user/**").hasAnyRole(AppConstants.ROLE_NORMAL, AppConstants.ROLE_ADMIN)
				.requestMatchers(HttpMethod.GET,"/product").permitAll()   
				.requestMatchers(HttpMethod.GET,"/category/**").permitAll()
				.requestMatchers(HttpMethod.POST,"/auth/generate-token").permitAll()
				.requestMatchers("/auth/**").authenticated()
				.requestMatchers("/category/**").hasRole(AppConstants.ROLE_ADMIN)
				.anyRequest().permitAll() // that line show the all request pass to the preAuthorize (Method level security)
				);
		
		
		// kis type ki security
//		security.httpBasic(Customizer.withDefaults());


      //entry point
      security.exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint));

      //session creation policy
      security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
      //main -->
      security.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
		
	}
	
	// for password encoder
	@Bean
	public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	}
	

	@Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	   return builder.getAuthenticationManager();
   }
	

}
