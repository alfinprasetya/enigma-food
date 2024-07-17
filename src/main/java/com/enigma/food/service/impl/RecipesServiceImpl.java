package com.enigma.food.service.impl;

import com.enigma.food.model.Recipes;
import com.enigma.food.repo.RecipesRepository;
import com.enigma.food.service.RecipesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Service
@RequiredArgsConstructor
public class RecipesServiceImpl implements RecipesService {
    private final RecipesRepository repository;
    @Override
    public List<Recipes> getAll() {
        return repository.findAll();
    }

    @Override
    public Recipes getOne(Integer id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Recipe with " + id + " Not found"));
    }

    @Override
    public Recipes create(Recipes req) {
        return repository.save(req);
    }

    @Override
    public Recipes update(Integer id, Recipes req) {
        Recipes recipe = this.getOne(id);
        recipe.setName(req.getName());
        recipe.setDescription(req.getDescription());
        recipe.setMethod(req.getMethod());
        return recipe;
    }

    @Override
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
