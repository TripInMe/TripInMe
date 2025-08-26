package com.example.TripInMe.controller;

import com.example.TripInMe.dto.LoginRequest;
import com.example.TripInMe.dto.SignupRequest;
import com.example.TripInMe.dto.UserResponse;
import com.example.TripInMe.repository.UserRepository;
import com.example.TripInMe.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService auth;

    public AuthController(AuthService auth) {this.auth = auth;}

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody @Valid SignupRequest req){
        return auth.signup(req);
    }

    @PostMapping("/login")
    public UserResponse login(@RequestBody @Valid LoginRequest req, HttpSession session){
        UserResponse user = auth.login(req);
        session.setAttribute("USER_ID", user.id);
        session.setAttribute("USERNAME", user.username);
        return user;
    }

    @PostMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @GetMapping("/me")
    public UserResponse me(HttpSession session){
        Object id = session.getAttribute("USER_ID");
        Object username = session.getAttribute("USERNAME");
        if(id == null || username == null){
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        }
        return new UserResponse((Long) id, (String) username, null);
    }

}
