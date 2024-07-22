package com.enigma.food.service.impl;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.service.AuthService;
import com.enigma.food.service.UserService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.TopUpDto;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import com.enigma.food.utils.specification.UserSpecification;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationService validationService;
    private final AuthService authService;

    @Override
    public User create(UserCreateDTO request) {
        validationService.validate(request);

        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        User newAdmin = new User();
        newAdmin.setUsername(request.getUsername());
        newAdmin.setPassword(passwordEncoder.encode(request.getPassword()));
        newAdmin.setBalance(request.getBalance());
        if (newAdmin.getRole() == null) {
            newAdmin.setRole(Role.ROLE_ADMIN);
        }
        return userRepository.save(newAdmin);
    }

    @Override
    public Page<User> getAll(String username, Integer minBalance, Integer maxBalance, Pageable pageable) {
        Specification<User> specification = UserSpecification.getSpecification(username, minBalance, maxBalance);
        return userRepository.findAll(specification, pageable);
    }

    @Override
    public User getOne(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found"));
    }

    @Override
    public User update(Integer id, UserUpdateDTO request) {
        validationService.validate(request);
        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        User user = this.getOne(id);
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            user.setPassword(request.getPassword());
        }
        if (request.getBalance() != null) {
            user.setBalance(request.getBalance());
        }
        return userRepository.save(user);

    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User topUp(TopUpDto req) {
        validationService.validate(req);
        User authenticatedUser = authService.getAuthenticatedUser();
        authenticatedUser.setBalance(authenticatedUser.getBalance() + req.getBalance());
        return userRepository.save(authenticatedUser);
    }
}
