package com.enigma.food.service;

import com.enigma.food.model.User;
import com.enigma.food.utils.dto.UserDTO;

import java.util.List;

public interface UserService {

    User create(UserDTO request);
    List<User> getAll();
    User getOne(Integer id);
    User update(Integer id, UserDTO request);
    void delete(Integer id);
}
