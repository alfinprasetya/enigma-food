package com.enigma.food.controller;

import com.enigma.food.model.User;
import com.enigma.food.service.UserService;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testCreateUser() throws Exception {
        UserCreateDTO userCreateDTO = new UserCreateDTO("newUser", "Password1@", 1000);

        User user = new User();
        user.setId(1);
        user.setUsername("newUser");
        user.setPassword("Password1@");
        user.setBalance(1000);

        when(userService.create(any(UserCreateDTO.class))).thenReturn(user);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.username", is("newUser")));

        verify(userService).create(any(UserCreateDTO.class));
    }

    @Test
    public void testGetAllUsers() throws Exception {
    }




    @Test
    public void testGetOneUser() throws Exception {
        User user = new User();
        user.setId(1);
        user.setUsername("user1");
        user.setBalance(1000);

        when(userService.getOne(1)).thenReturn(user);

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("user1")));

        verify(userService).getOne(1);
    }

    @Test
    public void testGetOneUserNotFound() throws Exception {
        when(userService.getOne(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserUpdateDTO userUpdateDTO = new UserUpdateDTO("updatedUser", "NewPassword1@", 2000);

        User user = new User();
        user.setId(1);
        user.setUsername("updatedUser");
        user.setPassword("NewPassword1@");
        user.setBalance(2000);

        when(userService.update(eq(1), any(UserUpdateDTO.class))).thenReturn(user);

        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username", is("updatedUser")));

        verify(userService).update(eq(1), any(UserUpdateDTO.class));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).delete(1);

        mockMvc.perform(delete("/users/1"))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(1);
    }
}
