package com.example.SpringSecurity.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service

public interface UserService {


    UserDetailsService userDetailsService();

}
