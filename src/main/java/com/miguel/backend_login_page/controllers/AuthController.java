package com.miguel.backend_login_page.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.miguel.backend_login_page.domain.user.User;
import com.miguel.backend_login_page.dto.LoginRequestDTO;
import com.miguel.backend_login_page.dto.RegisterRequestDTO;
import com.miguel.backend_login_page.dto.ResponseDTO;
import com.miguel.backend_login_page.infra.exception.InvalidCredentialsException;
import com.miguel.backend_login_page.infra.exception.UserAlreadyExistsException;
import com.miguel.backend_login_page.infra.exception.UserNotFoundException;
import com.miguel.backend_login_page.infra.security.TokenService;
import com.miguel.backend_login_page.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getName(), token));
        }
        throw new InvalidCredentialsException("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body) {
        Optional<User> user = this.repository.findByEmail(body.email());

        if (user.isPresent()) {
            throw new UserAlreadyExistsException("User already exists with this email");
        }

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setName(body.name());
        this.repository.save(newUser);
        String token = this.tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getName(), token));
    }
}
