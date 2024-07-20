package com.enigma.food.service.impl;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private List<User> mockUserList;

    @BeforeEach
    public void setUp() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setPassword("password1");
        user1.setBalance(1000);
        user1.setRole(Role.ROLE_USER);

        User user2 = new User();
        user2.setId(2);
        user2.setUsername("user2");
        user2.setPassword("password2");
        user2.setBalance(2000);
        user2.setRole(Role.ROLE_ADMIN);

        mockUserList = Arrays.asList(user1, user2);
    }

    @Test
    public void testCreateUser_Success() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("newuser");
        dto.setPassword("newpassword");
        dto.setBalance(3000);

        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("encodedpassword");
        newUser.setBalance(3000);
        newUser.setRole(Role.ROLE_ADMIN);

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedpassword");
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User result = userService.create(dto);

        assertEquals("newuser", result.getUsername());
        assertEquals(3000, result.getBalance());
        assertEquals(Role.ROLE_ADMIN, result.getRole());
        verify(userRepository).findByUsername(dto.getUsername());
        verify(passwordEncoder).encode(dto.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testCreateUser_UsernameTaken() {
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("user1");
        dto.setPassword("newpassword");
        dto.setBalance(3000);

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(new User()));

        ResponseStatusException thrown = assertThrows(ResponseStatusException.class, () -> userService.create(dto));

        assertEquals(HttpStatus.CONFLICT, thrown.getStatusCode());
        assertEquals("Username is already taken", thrown.getReason());
        verify(userRepository).findByUsername(dto.getUsername());
    }

    @Test
    public void testGetUserById_Success() {
        User user = mockUserList.get(0);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User result = userService.getOne(1);

        assertEquals(user, result);
        verify(userRepository).findById(1);
    }

    @Test
    public void testGetOne_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getOne(1));
    }

    @Test
    public void testUpdateUser_Success() {
        UserUpdateDTO dto = new UserUpdateDTO();
        dto.setUsername("updateduser");
        dto.setPassword("updatedpassword");
        dto.setBalance(4000);

        User existingUser = mockUserList.get(0);
        existingUser.setUsername("user1");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = userService.update(1, dto);

        assertEquals("updateduser", result.getUsername());
        assertEquals("updatedpassword", result.getPassword());
        assertEquals(4000, result.getBalance());
        verify(userRepository).findById(1);
        verify(userRepository).findByUsername(dto.getUsername());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testDelete_Success() {
        userService.delete(1);

        verify(userRepository).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")).when(userRepository).deleteById(1);

        assertThrows(ResponseStatusException.class, () -> userService.delete(1));
        verify(userRepository).deleteById(1);
    }

}