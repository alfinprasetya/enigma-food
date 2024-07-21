package com.enigma.food.service.impl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.security.JwtTokenProvider;
import com.enigma.food.service.AuthService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserLoginDTO;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final ValidationService validationService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public User register(UserCreateDTO req) {
    validationService.validate(req);
    if (userRepository.findByUsername(req.getUsername()).isPresent()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username is taken");
    }

    User newUser = new User();
    newUser.setUsername(req.getUsername());
    newUser.setPassword(passwordEncoder.encode(req.getPassword()));
    newUser.setBalance(req.getBalance());

    if (newUser.getRole() == null) {
      newUser.setRole(Role.ROLE_USER);
    }

    return userRepository.save(newUser);
  }

  @Override
  public String login(UserLoginDTO req) {
    validationService.validate(req);
    Authentication auth = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            req.getUsername(),
            req.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(auth);

    return jwtTokenProvider.generateToken(auth);
  }

  public User getAuthenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      Object principal = authentication.getPrincipal();
      if (principal instanceof UserDetails) {
        String username = ((UserDetails) principal).getUsername();
        return userRepository.findByUsername(username)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found"));
      }
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not Found");
  }

  @PostConstruct
  public void initAdmin() {
    String username = "admin";
    String password = "Admin123=";

    Optional<User> optionalUserCredential = userRepository.findByUsername(username);
    if (optionalUserCredential.isPresent()) {
      return;
    }

    Role roleAdmin = Role.ROLE_ADMIN;
    User userCredential = User.builder()
        .username(username)
        .password(passwordEncoder.encode(password))
        .role(roleAdmin)
        .build();
    userRepository.save(userCredential);
  }
}
