package com.enigma.food;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.enigma.food.controller.UserControllerTest;
import com.enigma.food.service.impl.ItemsServiceImplTest;
import com.enigma.food.service.impl.RecipesServiceImplTest;
import com.enigma.food.service.impl.UserServiceImplTest;

@Suite
@SelectClasses({ RecipesServiceImplTest.class, ItemsServiceImplTest.class, UserControllerTest.class,
    UserServiceImplTest.class })
public class TestSuite {
}
