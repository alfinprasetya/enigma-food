package com.enigma.food.service.impl;

import com.enigma.food.model.User;
import com.enigma.food.repository.UserRepository;
import com.enigma.food.service.AuthService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.TopUpDto;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ValidationService validationService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private AuthService authService;

    @Test
    public void testCreateUser_Success() {
        User user = mock(User.class);
        UserCreateDTO dto = new UserCreateDTO();
        dto.setUsername("alwi");
        dto.setPassword("password");
        dto.setBalance(3000);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        User saveUser = userService.create(dto);

        assertThat(saveUser).isNotNull();
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
    public void testGetAll() {
        String username1 = "testUser1";
        String username2 = "testUser2";
        String username3 = "testUser3";
        Integer minBalance = 100;
        Integer maxBalance = 1000;
        Pageable pageable = PageRequest.of(0, 2); // hal pertama dengan ukuran 2

        User user1 = new User();
        user1.setUsername(username1);
        user1.setBalance(500);

        User user2 = new User();
        user2.setUsername(username2);
        user2.setBalance(700);

        User user3 = new User();
        user3.setUsername(username3);
        user3.setBalance(900);

        // list dengan semua data
        List<User> usersList = Arrays.asList(user1, user2, user3);

        // sublist sesuai dengan ukuran halaman
        List<User> pageContent = usersList.subList(0, pageable.getPageSize());
        Page<User> usersPage = new PageImpl<>(pageContent, pageable, usersList.size());

        when(userRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(usersPage);

        Page<User> result = userService.getAll(null, minBalance, maxBalance, pageable);

        assertNotNull(result);
        assertEquals(3, result.getTotalElements()); // total elemen tetap 3
        assertEquals(2, result.getNumberOfElements()); // jumlah elemen dalam halaman sesuai ukuran halaman

        // elemen dalam halaman sesuai dengan ukuran halaman
        assertEquals(username1, result.getContent().get(0).getUsername());
        assertEquals(500, result.getContent().get(0).getBalance());
        assertEquals(username2, result.getContent().get(1).getUsername());
        assertEquals(700, result.getContent().get(1).getBalance());
    }

    @Test
    public void UserService_GetById_ReturnUser() {
        User user = mock(User.class);

        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(user));
        User foundUser = userService.getOne(1);

        assertThat(foundUser).isNotNull();
    }

    @Test
    public void testGetOne_NotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getOne(1));
    }

    @Test
    public void UserService_UpdateById_ReturnUpdatedUser() {
        User user = mock(User.class);
        UserUpdateDTO updateUser = new UserUpdateDTO();
        updateUser.setUsername("alwiya");
        updateUser.setPassword("Password@123");
        updateUser.setBalance(1000);

        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(user));
        when(userRepository.save(any(User.class)))
                .thenReturn(user);
        User updatedUser = userService.update(1, updateUser);

        assertThat(updatedUser).isNotNull();
    }

    @Test
    public void testDeleteUser_Success() {
        userService.delete(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDelete_NotFound() {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User Not Found")).when(userRepository).deleteById(1);

        assertThrows(ResponseStatusException.class, () -> userService.delete(1));
        verify(userRepository).deleteById(1);
    }

    @Test
    public void testTopUp_Success() {
        TopUpDto topUpDto = new TopUpDto();
        topUpDto.setBalance(500);
        User authenticatedUser = new User();
        authenticatedUser.setBalance(1000);

        when(authService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(userRepository.save(any(User.class))).thenReturn(authenticatedUser);

        User updatedUser = userService.topUp(topUpDto);

        assertNotNull(updatedUser);
        assertEquals(1500, updatedUser.getBalance());
        verify(authService, times(1)).getAuthenticatedUser();
        verify(userRepository, times(1)).save(authenticatedUser);
        verify(validationService, times(1)).validate(topUpDto);
    }

    @Test
    public void testTopUp_InvalidRequest() {
        TopUpDto topUpDto = new TopUpDto();
        topUpDto.setBalance(500);

        doThrow(new IllegalArgumentException("Invalid request")).when(validationService).validate(topUpDto);

        assertThrows(IllegalArgumentException.class, () -> userService.topUp(topUpDto));
        verify(validationService, times(1)).validate(topUpDto);
        verify(authService, never()).getAuthenticatedUser();
        verify(userRepository, never()).save(any(User.class));
    }

}