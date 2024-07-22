package com.enigma.food.service.impl;

import com.enigma.food.model.Ingridients;
import com.enigma.food.model.Items;
import com.enigma.food.model.Recipes;
import com.enigma.food.repository.IngridientsRepository;
import com.enigma.food.repository.RecipesRepository;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.RecipeCreatesDTO;
import jakarta.validation.Valid;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipesServiceImplTest {

    @Mock
    private RecipesRepository repository;

    @Mock
    private IngridientsRepository ingridientsRepository;
    @Mock
    private ValidationService validationService;

    @InjectMocks
    private IngridientsServiceImpl ingridientsService;
    @InjectMocks
    private RecipesServiceImpl recipesService;

    private List<Recipes> mockRecipesList;

    @BeforeEach
    public void setUp() {
        Recipes recipe1 = new Recipes();
        recipe1.setId(1);
        recipe1.setName("Recipe 1");
        recipe1.setPrice(5000);

        Recipes recipe2 = new Recipes();
        recipe2.setId(2);
        recipe2.setName("Recipe 2");
        recipe2.setPrice(10000);

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
    @Test
    public void createSuccess() {
        Items items = new Items();
        items.setId(1);
        items.setQty(10);
        Ingridients ing = new Ingridients();
        ing.setItem(items);
        ing.setQty(1);

        RecipeCreatesDTO dto = new RecipeCreatesDTO("Recipe 1",
                "Ayam, Minyak","Gorenglah Ayam",10000,);

        Recipes expectedRecipes = new Recipes();
        expectedRecipes.setName(dto.getName());
        expectedRecipes.setPrice(dto.getPrice());

        when(repository.save(any())).thenReturn(expectedRecipes);

        Recipes result = recipesService.create(dto);

        assertNotNull(result);
        assertEquals("Recipe 1", result.getName());
        assertEquals(10000, result.getPrice());
        verify(validationService, times(1)).validate(dto);
        verify(repository, times(1)).save(any(Recipes.class));
    }
}
