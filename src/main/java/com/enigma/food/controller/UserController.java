package com.enigma.food.controller;

import com.enigma.food.model.User;
import com.enigma.food.service.UserService;
import com.enigma.food.utils.PageWrapper;
import com.enigma.food.utils.Res;
import com.enigma.food.utils.dto.TopUpDto;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody UserCreateDTO request) {
        return Res.renderJson(
                userService.create(request),
                HttpStatus.CREATED,
                "created");
    }

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Integer minBalance,
            @RequestParam(required = false) Integer maxBalance,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return Res.renderJson(
                new PageWrapper<>(userService.getAll(username, minBalance, maxBalance, pageable)),
                HttpStatus.OK,
                "Succesfully get users");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        try {
            User user = userService.getOne(id);
            return Res.renderJson(
                    user,
                    HttpStatus.OK,
                    "Successfully got a user with id " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody UserUpdateDTO request) {
        return Res.renderJson(
                userService.update(id, request),
                HttpStatus.OK,
                "Successfully updated a user with id " + id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    @PostMapping("/topup")
    public ResponseEntity<?> TopUp(@RequestBody TopUpDto req) {
        return Res.renderJson(
                userService.topUp(req),
                HttpStatus.OK,
                "Top up success");
    }

}
