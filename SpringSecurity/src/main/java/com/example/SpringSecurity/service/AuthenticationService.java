package com.example.SpringSecurity.service;

import com.example.SpringSecurity.ServiceImpl.AuthenticationServiceImpl;
import com.example.SpringSecurity.dto.JwtAuthenticationResponse;
import com.example.SpringSecurity.dto.RefreshToken;
import com.example.SpringSecurity.dto.SigninRequest;
import com.example.SpringSecurity.dto.SignupRequest;
import com.example.SpringSecurity.entity.User;

public interface AuthenticationService  {

    User signup(SignupRequest signupRequest);
    JwtAuthenticationResponse signin(SigninRequest signinRequest);
    JwtAuthenticationResponse refreshToken(RefreshToken refreshToken);

}
