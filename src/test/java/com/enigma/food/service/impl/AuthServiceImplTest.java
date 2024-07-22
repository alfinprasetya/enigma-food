package com.enigma.food.service.impl;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.security.JwtTokenProvider;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserLoginDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private ValidationService validationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    public void testRegister_Success() {
        UserCreateDTO req = new UserCreateDTO();
        req.setUsername("user");
        req.setPassword("password");
        req.setBalance(1000);

        User newUser = new User();
        newUser.setUsername(req.getUsername());
        newUser.setPassword(req.getPassword());
        newUser.setBalance(req.getBalance());
        newUser.setRole(Role.ROLE_USER);

        when(userRepository.findByUsername(req.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(req.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User registeredUser = authService.register(req);

        assertNotNull(registeredUser);
        assertEquals("user", registeredUser.getUsername());
        assertEquals(1000, registeredUser.getBalance());
        assertEquals(Role.ROLE_USER, registeredUser.getRole());

        verify(validationService, times(1)).validate(req);
        verify(userRepository, times(1)).findByUsername(req.getUsername());
        verify(passwordEncoder, times(1)).encode(req.getPassword());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testRegister_UsernameTaken() {
        UserCreateDTO req = new UserCreateDTO();
        req.setUsername("user");
        req.setPassword("password");
        req.setBalance(1000);

        when(userRepository.findByUsername(req.getUsername())).thenReturn(Optional.of(new User()));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> authService.register(req));

        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatusCode());
        assertEquals("username is taken", thrown.getReason());

        verify(validationService, times(1)).validate(req);
        verify(userRepository, times(1)).findByUsername(req.getUsername());
        verify(passwordEncoder, never()).encode(anyString());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testLogin_Success() {
        UserLoginDTO req = new UserLoginDTO();
        req.setUsername("user");
        req.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn("token");

        String token = authService.login(req);

        assertNotNull(token);
        assertEquals("token", token);

        verify(validationService, times(1)).validate(req);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider, times(1)).generateToken(authentication);
    }

    @Test
    public void testGetAuthenticatedUser_Success() {
        User user = new User();
        user.setUsername("user");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("user");
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        User result = authService.getAuthenticatedUser();

        assertNotNull(result);
        assertEquals("user", result.getUsername());

        verify(userRepository, times(1)).findByUsername("user");
    }

    @Test
    public void testGetAuthenticatedUser_NotFound() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn("invalidUser");
        when(authentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(ResponseStatusException.class, () -> authService.getAuthenticatedUser());
        verify(userRepository, never()).findByUsername(anyString());
    }

    @Test
    public void testInitAdmin_UserExists() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(new User()));

        authService.initAdmin();

        verify(userRepository, times(1)).findByUsername("admin");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testInitAdmin_NewUser() {
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("Admin123=")).thenReturn("encodedPassword");

        authService.initAdmin();

        verify(userRepository, times(1)).findByUsername("admin");
        verify(userRepository, times(1)).save(any(User.class));
    }
}
