package com.krishna.safejourney.config;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

//This Class generates the Jwt token
@Component
public class JwtUtil {
	
//	Must be 32+ characters
    private final String SECRET = "your-secret-key-very-long-your-secret-key-very-long";
    
    
//   Create key Converts String → Key object
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}

//	  Generate Token  Runs when user logs in
	public String generateToken(String email) {
      String s= Jwts.builder() //Starts creating token
              .setSubject(email) //Stores user email inside token
              .setIssuedAt(new Date())//Token creation time
              .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))//Token valid for 1 hour
              .signWith(getSigningKey())   //Signature = HMACSHA256("abc.xyz" + SECRET_KEY)
              .compact();//Converts into final token string
      System.out.println(s);
      return s;
	}
	

//     Extract Email
    public String extractEmail(String token) {
        return Jwts.parserBuilder()   // Prepares JWT to be decoded
                .setSigningKey(getSigningKey())//Uses same key used during signing and Verifies token is not tampered
                .build()//finalizes parser setup
                .parseClaimsJws(token) //decodes token by validating signature+expiry details+decode payload if invalid throw exception
                .getBody()//extracts data part(payload) 
//                ex:
//                {
//                "sub": "yugesh_123",
//                "iat": 2345678987,
//                "exp": 1234567890
//                }
                .getSubject(); 
//                "yugesh_123"
	}
//  MUST HAVE (this was missing)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token); // validates everything
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}



