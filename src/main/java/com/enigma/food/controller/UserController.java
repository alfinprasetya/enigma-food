package com.enigma.food.controller;

import com.enigma.food.model.User;
import com.enigma.food.service.UserService;
import com.enigma.food.utils.Res;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        return Res.renderJson(
                userService.getAll(),
                HttpStatus.OK,
                "Succesfully get users"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id) {
        try {
            User user = userService.getOne(id);
            return Res.renderJson(
                    user,
                    HttpStatus.OK,
                    "Successfully got a user with id " + id
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody User request) {
        return Res.renderJson(
                userService.update(id, request),
                HttpStatus.OK,
                "Successfully updated a user with id " + id
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }
}
