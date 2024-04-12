package com.example.SpringSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class SigninRequest {

    private String password;


    public String getPassword() {

        return password;
    }

}
