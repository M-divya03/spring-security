package com.example.SpringSecurity.controller;

import com.example.SpringSecurity.dto.JwtAuthenticationResponse;
import com.example.SpringSecurity.dto.RefreshToken;
import com.example.SpringSecurity.dto.SigninRequest;
import com.example.SpringSecurity.dto.SignupRequest;
import com.example.SpringSecurity.entity.User;
import com.example.SpringSecurity.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationService authenticationService;
    @PostMapping("/api/signup")
    public ResponseEntity<User> signup(@RequestBody SignupRequest signupRequest){
        return ResponseEntity.ok(authenticationService.signup(signupRequest));

    }

    @PostMapping("/api/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/api/refreshtoken")
    public ResponseEntity<JwtAuthenticationResponse> refreshtoken(@RequestBody RefreshToken refreshToken){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    @GetMapping("/user_only")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<String> userOnly(){

        return ResponseEntity.ok("hello user");
    }

}
