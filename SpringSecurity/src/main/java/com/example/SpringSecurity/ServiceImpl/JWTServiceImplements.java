//package com.example.SpringSecurity.ServiceImpl;
//
//import com.example.SpringSecurity.service.JWTService;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Service;
//
//import java.security.*;
//import java.security.spec.ECGenParameterSpec;
//import java.util.Date;
//import java.util.Map;
//import java.util.function.Function;
//
//import static io.jsonwebtoken.impl.crypto.EllipticCurveProvider.generateKeyPair;
//
//@Service
//public class JWTServiceImplements implements JWTService {
//
//private KeyPair keyPair;
//    public JWTServiceImplements() {
//        // Initialize keyPair here
//        keyPair = generateKeyPair(); // Example method to generate KeyPair
//    }
//
//    private PrivateKey getSignatureKey() {
//        try {
//            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
//            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
//            keyPairGenerator.initialize(ecSpec);
//            KeyPair keyPair = keyPairGenerator.generateKeyPair();
//            return keyPair.getPrivate();
//        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public String generateToken(UserDetails userDetails) {
//
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
//                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
//                .compact();
//    }
//
//    public String generateRefreshToken(Map<String,Object> extraClaims, UserDetails userDetails){
//        return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
//                .signWith(getSignatureKey(), SignatureAlgorithm.ES256)
//                .compact();
//    }
//
//    private <T> T extractClaim(String token, Function<Claims,T>claimsResolvers){
//        final Claims claims = extractAllClaim(token);
//        return claimsResolvers.apply(claims);
//    }
//
//    public String extractUserName(String token){
//        return extractClaim(token,Claims::getSubject);
//    }
//
//
//
//    private Claims extractAllClaim(String token) {
//        return Jwts.parserBuilder().setSigningKey(getSignatureKey()).build().parseClaimsJws(token).getBody();
//    }
//
//   /* private Key getSignatureKey() {
//        byte[] key= Decoders.BASE64.decode("413FG442847284B625065536856790254267863274903249056456789534ty");
//        return Keys.hmacShaKeyFor(key);
//    }*/
//
//
//
//    public boolean isTokenValid(String token,UserDetails userDetails){
//        final String username=extractUserName(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractClaim(token,Claims::getExpiration).before(new Date());
//    }
//}
//




package com.example.SpringSecurity.ServiceImpl;

import com.example.SpringSecurity.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
public class JWTServiceImplements implements JWTService {

 /*   private KeyPair keyPair;

    public JWTServiceImplements() {

        this.keyPair = generateKeyPair();
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
            keyPairGenerator.initialize(ecSpec);
            return keyPairGenerator.generateKeyPair();

        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String generateToken(UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.ES256)
                .compact();
    }*/




  /*  private Claims extractAllClaim(String token) {

        return Jwts
                .parserBuilder()
                .setSigningKey(keyPair.getPublic())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }*/


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    public String extractUserName(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token){
        return Jwts.
                parserBuilder()
                .setSigningKey(getSignatureKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

     private Key getSignatureKey() {
         byte[] key = Decoders.BASE64.decode("iwXXoaFSeQu0hAIcdkXPghuO2YL00i7zMc6k8hkviMRZGFDbheMte8Sfwwg342BD");
         return Keys.hmacShaKeyFor(key);
     }

     public String generateToken(UserDetails userDetails){

        return generateToken(new HashMap<>(),userDetails);
     }
     public String generateToken(Map<String,Object> extraClaims,UserDetails userDetails){
         Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
         List<String> roles = authorities.stream()
                 .map(GrantedAuthority::getAuthority)
                 .toList();

         return Jwts
                .builder()
                 .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                 .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getSignatureKey(),SignatureAlgorithm.HS256)
                .compact();
     }

    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        List<String> roles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts.
                builder().
                setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .claim("roles", roles)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


}
