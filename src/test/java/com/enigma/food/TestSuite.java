package com.enigma.food;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.enigma.food.service.impl.RecipesServiceImplTest;

@Suite
@SelectClasses({RecipesServiceImplTest.class})
public class TestSuite {

}
