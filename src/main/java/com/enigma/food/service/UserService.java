package com.enigma.food.service;

import com.enigma.food.model.User;
import com.enigma.food.utils.dto.TopUpDto;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User create(UserCreateDTO request);
    Page<User> getAll(String username, Integer minBalance, Integer maxBalance, Pageable pageable);
    User getOne(Integer id);
    User update(Integer id, UserUpdateDTO request);
    void delete(Integer id);
    User topUp(TopUpDto req);
}
