package com.example.TripInMe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SendMessageRequest {
    @NotNull
    public Long toUserId;

    @NotBlank @Size(max = 5000)
    public String body;
}
