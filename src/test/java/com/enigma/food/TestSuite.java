package com.enigma.food;

import com.enigma.food.controller.OrderControllerTest;
import com.enigma.food.service.impl.*;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.enigma.food.controller.ItemsControllerTest;
import com.enigma.food.controller.RecipeControllerTest;
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
        OrderServiceImplTest.class,
        ItemsControllerTest.class,
        RecipeControllerTest.class,
        OrderControllerTest.class
})
public class TestSuite {
}
