package com.example.SpringSecurity.ServiceImpl;

import com.example.SpringSecurity.dto.JwtAuthenticationResponse;
import com.example.SpringSecurity.dto.RefreshToken;
import com.example.SpringSecurity.dto.SigninRequest;
import com.example.SpringSecurity.dto.SignupRequest;
import com.example.SpringSecurity.entity.Role;
import com.example.SpringSecurity.entity.User;
import com.example.SpringSecurity.repo.UserRepo;
import com.example.SpringSecurity.service.AuthenticationService;
import com.example.SpringSecurity.service.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;



    public User signup(SignupRequest signupRequest){
        User user=new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        var jwtToken=jwtService.generateToken(user);
     return userRepo.save(user);




    }
 //   public JwtAuthenticationResponse register(RegisterRequest registerRequest)




    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        // Find user by password
        User user = userRepo.findByPassword(signinRequest.getPassword())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate JWT token and refresh token
        String jwt = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        // Create authentication response
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);

        return jwtAuthenticationResponse;
    }


    public JwtAuthenticationResponse refreshToken(RefreshToken refreshToken){
        String userEmail=jwtService.extractUserName(refreshToken.getToken());
        User user=userRepo.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshToken.getToken(),user)){
            String jwt=jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());

            return jwtAuthenticationResponse;
        }
        return null;
    }


}
