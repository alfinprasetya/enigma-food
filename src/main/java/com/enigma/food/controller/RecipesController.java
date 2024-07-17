package com.enigma.food.controller;

import com.enigma.food.model.Recipes;
import com.enigma.food.service.RecipesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipesController {
    private final RecipesService service;

    @GetMapping
    public List<Recipes> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Recipes getOne(@PathVariable Integer id){
        return service.getOne(id);
    }

    @PostMapping
    public Recipes create(@RequestBody Recipes req){
        return service.create(req);
    }

    @PutMapping("/{id}")
    public Recipes update(@PathVariable Integer id, @RequestBody Recipes req){
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}
