package com.example.SpringSecurity.ServiceImpl;

import com.example.SpringSecurity.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

@Service
public class RoleAccessService {
    public String getMessage(String token) {
        Claims claims = extractClaims(token);
        Role role = extractRole(claims);

        // Access control based on the role
        if (role == Role.ADMIN) {
            return "Welcome, Admin!";
        } else if (role == Role.USER) {
            return "Welcome, User!";
        } else {
            return "Unauthorized";
        }
    }

    // Method to extract claims from the JWT token
    private Claims extractClaims(String token) {
        String[] parts = token.split("\\.");
        String decodedPayload = new String(java.util.Base64.getUrlDecoder().decode(parts[1]));
        return Jwts.parser().parseClaimsJwt(decodedPayload).getBody();
    }

    // Method to extract role from the claims
    private Role extractRole(Claims claims) {
        String roleString = claims.get("roles", String.class); // Assuming roles are stored as a String in the token
        return Role.valueOf(roleString); // Assuming roleString matches enum values
    }
}
