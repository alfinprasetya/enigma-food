package com.enigma.food.service.impl;

import com.enigma.food.model.Recipes;
import com.enigma.food.repository.RecipesRepository;
import com.enigma.food.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipesServiceImplTest {

    @Mock
    private RecipesRepository repository;

    @Mock
    private ValidationService validationService;

    @InjectMocks
    private RecipesServiceImpl recipesService;

    private List<Recipes> mockRecipesList;

    @BeforeEach
    public void setUp() {
        Recipes recipe1 = new Recipes();
        recipe1.setId(1);
        recipe1.setName("Recipe 1");

        Recipes recipe2 = new Recipes();
        recipe2.setId(2);
        recipe2.setName("Recipe 2");

        mockRecipesList = Arrays.asList(recipe1, recipe2);
    }

    @Test
    public void testGetOne_Success() {
        Recipes recipe = new Recipes();
        recipe.setId(1);
        recipe.setName("Recipe 1");

        when(repository.findById(1)).thenReturn(Optional.of(recipe));

        Recipes result = recipesService.getOne(1);

        assertEquals(recipe, result);
    }

    @Test
    public void testGetOne_NotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> recipesService.getOne(1));
    }
}
