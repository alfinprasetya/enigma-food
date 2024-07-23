package com.enigma.food.controller;

import com.enigma.food.model.Recipes;
import com.enigma.food.service.RecipesService;
import com.enigma.food.utils.dto.RecipeCreatesDTO;
import com.enigma.food.utils.dto.RecipeUpdatesDTO;
import com.enigma.food.utils.dto.UserCreateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerTest {
    @Mock
    private RecipesService recipesService;

    @InjectMocks
    private RecipesController recipesController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(recipesController).build();
    }

    @Test
    public void testCreateRecipe() throws  Exception {
        RecipeCreatesDTO.Item item  = new RecipeCreatesDTO.Item(1, 1);
        RecipeCreatesDTO recipeCreatesDTO = new RecipeCreatesDTO();
        recipeCreatesDTO.setName("Recipe 1");
        recipeCreatesDTO.setPrice(5000);
        recipeCreatesDTO.setMethod("Goreng Ayam");
        recipeCreatesDTO.setDescription("Ayam, Minyak");
        recipeCreatesDTO.setIngredients(Collections.singletonList(item));

        Recipes recipes = new Recipes();
        recipes.setId(1);
        recipes.setName("Recipe 1");
        recipes.setDescription("Ayam, Minyak");
        recipes.setMethod("Goreng Ayam");
        recipes.setPrice(5000);

        when(recipesService.create(any(RecipeCreatesDTO.class))).thenReturn(recipes);

        mockMvc.perform(post("/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeCreatesDTO)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.data.name", is("Recipe 1")));
        verify(recipesService).create(any(RecipeCreatesDTO.class));
    }
    @Test
    public void testGetAllRecipe()throws Exception{
    }

    @Test
    public void testGetOneUser()throws Exception{
        Recipes recipes = new Recipes();
        recipes.setId(1);
        recipes.setName("Recipe 1");
        recipes.setDescription("Ayam, Minyak");
        recipes.setMethod("Goreng Ayam di Wajan");
        recipes.setPrice(5000);

        when(recipesService.getOne(1)).thenReturn(recipes);

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isOk());
    }
    @Test
    public void testGetOneRecipeNotFound() throws Exception {
        when(recipesService.getOne(1)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found"));

        mockMvc.perform(get("/recipes/1"))
                .andExpect(status().isNotFound());
    }
    @Test
    public void testUpdateRecipe() throws  Exception {
        RecipeCreatesDTO.Item item  = new RecipeCreatesDTO.Item(1, 1);
        RecipeCreatesDTO recipeCreatesDTO = new RecipeCreatesDTO();
        recipeCreatesDTO.setName("Recipe 1");
        recipeCreatesDTO.setPrice(5000);
        recipeCreatesDTO.setMethod("Goreng Ayam");
        recipeCreatesDTO.setDescription("Ayam, Minyak");
        recipeCreatesDTO.setIngredients(Collections.singletonList(item));

        Recipes recipes = new Recipes();
        recipes.setId(1);
        recipes.setName("Recipe 1");
        recipes.setDescription("Ayam, Minyak");
        recipes.setMethod("Goreng Ayam");
        recipes.setPrice(5000);

        when(recipesService.update(eq(1), any(RecipeUpdatesDTO.class))).thenReturn(recipes);

        mockMvc.perform(put("/recipes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeCreatesDTO)))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.data.name", is("Recipe 1")));
        verify(recipesService).update(eq(1), any(RecipeUpdatesDTO.class));
    }
    @Test
    public void testDeleteRecipe() throws Exception {
        doNothing().when(recipesService).delete(1);

        mockMvc.perform(delete("/recipes/1"))
                .andExpect(status().isOk());

        verify(recipesService, times(1)).delete(1);
    }

}
