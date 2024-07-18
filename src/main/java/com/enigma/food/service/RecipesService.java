package com.enigma.food.service;

import com.enigma.food.model.Recipes;
import com.enigma.food.utils.dto.RecipeCreatesUpdatesDTO;

import java.util.List;

public interface RecipesService {
    List<Recipes> getAll();

    Recipes getOne(Integer id);
    Recipes create(RecipeCreatesUpdatesDTO req);
    Recipes update(Integer id, RecipeCreatesUpdatesDTO req);

    void delete(Integer id);

}
