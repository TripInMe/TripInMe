package com.example.TripInMe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePostRequest {
    @NotBlank @Size(min = 1, max = 120)
    public String title;

    @NotBlank @Size(min = 1, max = 10000)
    public String content;
}
