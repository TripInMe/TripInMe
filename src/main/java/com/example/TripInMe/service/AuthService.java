package com.example.TripInMe.service;

import com.example.TripInMe.dto.LoginRequest;
import com.example.TripInMe.dto.SignupRequest;
import com.example.TripInMe.dto.UserResponse;
import com.example.TripInMe.entity.User;
import com.example.TripInMe.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    private final UserRepository users;
    private final PasswordEncoder encoder;


    public AuthService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Transactional
    public UserResponse signup(SignupRequest req){
        if(users.existsByUsername(req.username)){
            throw new IllegalArgumentException("이미 사용중인 사용자명입니다.");
        }
        if(users.existsByEmail(req.email)){
            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
        }

        User u = new User();
        u.setUsername(req.username);
        u.setEmail(req.email);
        u.setPasswordHash(encoder.encode(req.password));
        User saved = (User) users.save(u);
        return new UserResponse(saved.getId(), saved.getUsername(), saved.getEmail());
    }

    @Transactional(readOnly = true)
    public UserResponse login(LoginRequest req){
        User u = users.findByUsername(req.usernameOrEmail)
                .or(() -> users.findByEmail(req.usernameOrEmail))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if(!encoder.matches(req.password, u.getPasswordHash())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new UserResponse(u.getId(), u.getUsername(), u.getEmail());
    }

}
