package com.example.TripInMe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SignupRequest {
    @NotBlank @Size(min = 3, max = 30)
    public String username;

    @NotBlank @Email @Size(max = 120)
    public String email;

    @NotBlank @Size(min = 6, max = 72)
    public String password;


}
