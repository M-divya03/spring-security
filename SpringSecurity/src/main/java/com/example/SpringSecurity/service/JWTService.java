package com.example.SpringSecurity.service;

import com.example.SpringSecurity.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUserName(String token);
    String generateToken(UserDetails userDetails);

   boolean isTokenValid(String token, UserDetails userDetails) ;

    String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails);

    Claims extractAllClaims(String token);

}
