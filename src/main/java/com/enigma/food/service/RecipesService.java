package com.enigma.food.service;

import com.enigma.food.model.Recipes;
import com.enigma.food.utils.dto.RecipeCreatesDTO;
import com.enigma.food.utils.dto.RecipeUpdatesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecipesService {
    Page<Recipes> getAll(String name, Integer price, Pageable pageable);

    Recipes getOne(Integer id);
    Recipes create(RecipeCreatesDTO req);
    Recipes update(Integer id, RecipeUpdatesDTO req);

    void delete(Integer id);

}
