package com.raj.project.secuirty;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	 private Logger logger = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

	    @Autowired
	    private JwtHelper jwtHelper;

	    @Autowired
	    private UserDetailsService userDetailsService;

	    
//	    @Override
//	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//
//	        // we will follow functional style of coding--> lambda
//
//
//	        Optional.ofNullable(request.getHeader(AppConstants.JWT_HEADER_NAME))
//	                .filter(header -> header.startsWith("Bearer "))
//	                .map(header -> header.substring(7))
//	                .ifPresentOrElse(token -> {
//	                    //token aa chuka hai
//	                    try {
//
//	                        //perform operations on token
//	                        Optional.ofNullable(jwtHelper.getUsernameFromToken(token))
//	                                .ifPresent(username -> {
//	                                    logger.info("Token username is {} ", username);
//	                                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
//	                                        //load user detail form database
//	                                        Optional.ofNullable(userDetailsService.loadUserByUsername(username))
//	                                                .filter(userDetails -> {
//	                                                    boolean isValidUser = username.equals(userDetails.getUsername());
//	                                                    boolean isTokenValid = !jwtHelper.isTokenExpired(token);
//	                                                    return isTokenValid && isValidUser;
//
//	                                                })
//	                                                .ifPresent(userDetails -> {
//	                                                    var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//	                                                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//	                                                    SecurityContextHolder.getContext().setAuthentication(authentication);
//	                                                });
//
//	                                    }
//	                                });
//
//
//	                    } catch (IllegalArgumentException ex) {
//	                        logger.info("Illegal Argument while fetching the username !! {}", ex.getMessage());
//	                    } catch (ExpiredJwtException ex) {
//	                        logger.info("Given jwt is expired !! {}", ex.getMessage());
//	                    } catch (MalformedJwtException ex) {
//	                        logger.info("Some changes have been done to the token !! Invalid Token {}", ex.getMessage());
//	                    } catch (Exception ex) {
//	                        logger.error("An unexpected error occurred while processing the token", ex);
//	                    }
//
//	                }, () -> {
//	                    //value empty
//	                    logger.info("Invalid Header !! Header is not starting with Bearer");
//	                });
//
//	        filterChain.doFilter(request, response);
//
//	    }


	//
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	
	        // api se pahle chalega jwt header: usko varify karne ke lie
	
	        //Authorization : Bearer hohkjsdfhobfnsfhoah
	        String requestHeader = request.getHeader("Authorization");
	        logger.info("Header {} ", requestHeader);
	        String username = null;
	        String token = null;
	        
	        if (requestHeader != null && requestHeader.startsWith("Bearer")) {
	            //thik hai sab: process...
	            token = requestHeader.substring(7); // remove 7 digit form Bearer hohkjsdfhobfnsfhoah
	            try {
	                username = jwtHelper.getUsernameFromToken(token);
	                logger.info("Token Username : {}", username);
	
	            } catch (IllegalArgumentException ex) {
	                logger.info("Illegal Argument while fetching the username !!" + ex.getMessage());
	            } catch (ExpiredJwtException ex) {
	                logger.info("Given jwt is  expired !!" + ex.getMessage());
	            } catch (MalformedJwtException ex) {
	                logger.info("Some changed has done in token !! Invalid Token" + ex.getMessage());
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	        } else {
	            logger.info("Invalid Header !! Header is not starting with Bearer");
	        }
	        // agar username null nhi hai to
	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            // username kuch  hai
	            //authentication null
	            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	
	            //validate token
	            if (username.equals(userDetails.getUsername()) && !jwtHelper.isTokenExpired(token)) {
	                // token valid
	                //security context ke ander authentication set karenge
	                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	
	            }
	
	        }
	
	        filterChain.doFilter(request, response);
	
	
	    }
}
