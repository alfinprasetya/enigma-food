package com.enigma.food.service;

import com.enigma.food.model.Role;
import com.enigma.food.model.User;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface UserService {

    User create(UserCreateDTO request);
    List<User> getAll(String username, Integer minBalance, Integer maxBalance);
    User getOne(Integer id);
    User update(Integer id, UserUpdateDTO request);
    void delete(Integer id);
}
