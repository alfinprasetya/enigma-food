package com.enigma.food.service;

import com.enigma.food.model.Recipes;

import java.util.List;

public interface RecipesService {
    List<Recipes> getAll();

    Recipes getOne(Integer id);
    Recipes create(Recipes req);
    Recipes update(Integer id, Recipes req);

    void delete(Integer id);

}
