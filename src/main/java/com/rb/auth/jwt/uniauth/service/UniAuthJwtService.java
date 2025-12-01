package com.rb.auth.jwt.uniauth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class UniAuthJwtService {

    private String secretKey = "this-is-secret-123456 need to add a lot more characters to a=m make it really really llong";
    private Long expirationTimeOfToken = 6000000L; // in milliseconds



    //Building a new JWT Token Logic
    public String generateNewJwtToken(String username, Map<String, Object> extraClaims){
        return buildNewJwtToken(username, extraClaims);
    }

    private String buildNewJwtToken(String username, Map<String, Object> extraClaims){
        return Jwts.builder()
                .setClaims(extraClaims)
                .setExpiration(new Date(System.currentTimeMillis()+ expirationTimeOfToken))
                .setSubject(username)
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .setIssuedAt(new Date())
                .compact();
    }

    //Extraction Logic
    public String extractUsernameFromJwtToken(String token){
        return extractAllClaimsFromToken(token).getSubject();
    }

    private Date extractExpirationFromJwtToken(String token){
        return extractAllClaimsFromToken(token).getExpiration();
    }

    private Claims extractAllClaimsFromToken(String token){
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
        catch (Exception e){
           return null;
        }
    }

    //Getting the singnin key
    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    //Toke validation logic
    private boolean isJwtTokenExpired(String token){
        return extractExpirationFromJwtToken(token).before(new Date(System.currentTimeMillis()));
    }

    public boolean isJwtTokenValid(String token, UserDetails userDetails){
        final String username = extractUsernameFromJwtToken(token);
        return (username.equals(userDetails.getUsername())) && !isJwtTokenExpired(token);
    }

}
