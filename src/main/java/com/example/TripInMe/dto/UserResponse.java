package com.example.TripInMe.dto;

public class UserResponse {
    public Long id;
    public String username;
    public String email;

    public UserResponse(Long id,String username, String email){
        this.id=id;
        this.username=username;
        this.email=email;
    }
}
