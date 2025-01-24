package com.raj.project.secuirty;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


// this class is used to perform jwt operations
@Component
public class JwtHelper 
{
	// VALIDITY IN milisec
	public static final long TOKEN_VALIDITY=5*60*60*1000;
	
	
	// secret key -> we put value in the application properties
	
	public static final String SECRET_KEY="vvbdfuhgukdfhgukdfhgukdfhgkudvbdfuhgukvbdfuhgukdfhgukdfhgukdfhgkudfhngukdfngkgrhidfhgukdfhgukdfhgkudfhngukdfngkgrhifhngukdfngkgrhibdfuhgukdfhgukdfhgukdfhgkudfhngukdfngkgrhi";
	
	// retrieve username from jwt token
	 //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
   
	private Claims getAllClaimsFromToken(String token) {
//        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();

        // 0.12.6
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();


//        latest
//        SignatureAlgorithm hs512 = SignatureAlgorithm.HS512;
//        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY.getBytes(), hs512.getJcaName());
//        return Jwts.parser().verifyWith(secretKeySpec).build().parseClaimsJws(token).getPayload();
    }


    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //generate token for user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return
                Jwts.builder()
                        .setClaims(claims).
                        setSubject(subject).
                        setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                        .signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
    }



}
