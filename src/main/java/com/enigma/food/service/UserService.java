package com.enigma.food.service;

import com.enigma.food.model.User;

import java.util.List;

public interface UserService {

    User create(User request);
    List<User> getAll();
    User getOne(Integer id);
    User update(Integer id, User request);
    void delete(Integer id);
}
