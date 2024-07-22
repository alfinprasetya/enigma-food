package com.enigma.food.controller;

import com.enigma.food.model.User;
import com.enigma.food.service.AuthService;
import com.enigma.food.utils.Res;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserLoginDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO request) {
        return Res.renderJson(
                authService.register(request),
                HttpStatus.CREATED,
                "Successfully Registered!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO request) {
        String jwt = authService.login(request);
        Map<String, String> response = new HashMap<>();
        response.put("token", jwt);

        return Res.renderJson(
                response,
                HttpStatus.OK,
                "Login Successfully");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getAuthenticatedUser() {
        return Res.renderJson(
                authService.getAuthenticatedUser(),
                HttpStatus.OK,
                "User profile");
    }

}
