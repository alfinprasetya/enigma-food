package com.enigma.food.service;

import com.enigma.food.model.Recipes;
import com.enigma.food.utils.dto.RecipeCreatesDTO;

import java.util.List;

public interface RecipesService {
    List<Recipes> getAll();

    Recipes getOne(Integer id);
    Recipes create(RecipeCreatesDTO req);
    Recipes update(Integer id, Recipes req);

    void delete(Integer id);

}
