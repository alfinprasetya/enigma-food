package com.enigma.food.service;

import com.enigma.food.model.User;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.enigma.food.utils.dto.UserLoginDTO;

public interface AuthService {
  User register(UserCreateDTO req);

  String login(UserLoginDTO req);

  User getAuthenticatedUser();
}
