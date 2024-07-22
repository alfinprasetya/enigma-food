package com.enigma.food;

import com.enigma.food.service.impl.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.enigma.food.controller.UserControllerTest;

@Suite
@SelectClasses({
        RecipesServiceImplTest.class,
        ItemsServiceImplTest.class,
        UserControllerTest.class,
        UserServiceImplTest.class,
        IngridientsServiceImplTest.class,
        OrderDetailServiceImplTest.class,
        AuthServiceImplTest.class,
        OrderServiceImplTest.class
})
public class TestSuite {
}
