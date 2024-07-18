package com.enigma.food.service.impl;

import com.enigma.food.model.Recipes;
import com.enigma.food.repository.RecipesRepository;
import com.enigma.food.service.RecipesService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.RecipeCreatesDTO;
import com.enigma.food.utils.dto.RecipeUpdatesDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private final RecipesRepository repository;
    private final ValidationService validationService;

    @Override
    public List<Recipes> getAll() {
        return repository.findAll();
    }

    @Override
    public Recipes getOne(Integer id) {
        return repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe with " + id + " Not found"));
    }

    @Override
    public Recipes create(RecipeCreatesDTO req) {
        validationService.validate(req);
        Recipes recipes = new Recipes();
        recipes.setName(req.getName());
        recipes.setDescription(req.getDescription());
        recipes.setMethod(req.getMethod());
        recipes.setPrice(req.getPrice());
        return repository.save(recipes);
    }

    @Override
    public Recipes update(Integer id, RecipeUpdatesDTO req) {
        validationService.validate(req);
        Recipes recipes = this.getOne(id);

        if (req.getName() != null) {
            recipes.setName(req.getName());
        }
        if (req.getDescription() != null) {
            recipes.setDescription(req.getDescription());
        }
        if (req.getMethod() != null) {
            recipes.setMethod(req.getMethod());
        }
        if (req.getPrice() != null) {
            recipes.setPrice(req.getPrice());
        }
        return repository.save(recipes);
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
