package com.enigma.food.service.impl;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.service.UserService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.UserDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;

    @Override
    public User create(UserDTO request) {
        validationService.validate(request);

        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        User newAdmin = new User();
        newAdmin.setUsername(request.getUsername());
        newAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
        if (newAdmin.getRole() == null) {
            newAdmin.setRole(Role.ROLE_ADMIN);
        }
        return userRepository.save(newAdmin);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @Override
    public User update(Integer id, UserDTO request) {
        validationService.validate(request);
        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        User user = this.getOne(id);
        user.setUsername(request.getUsername());
        userRepository.save(user);
        return user;
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
