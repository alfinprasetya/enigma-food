package com.enigma.food.controller;

import com.enigma.food.model.Recipes;
import com.enigma.food.service.RecipesService;
import com.enigma.food.utils.PageWrapper;
import com.enigma.food.utils.Res;
import com.enigma.food.utils.dto.RecipeCreatesDTO;
import com.enigma.food.utils.dto.RecipeUpdatesDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipesController {
    private final RecipesService service;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestParam(required = false)String name, @RequestParam(required = false) Integer price, @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<Recipes> recipesPage = service.getAll(name, price, pageable);
        PageWrapper<Recipes> result = new PageWrapper<>(recipesPage);
        return Res.renderJson(result,HttpStatus.OK, "Recipe Found");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        Recipes recipes = service.getOne(id);
        return Res.renderJson(recipes, HttpStatus.OK, "Success");
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RecipeCreatesDTO req){
        Recipes recipes = service.create(req);
        return Res.renderJson(recipes, HttpStatus.OK, "Recipes Create Successfully");
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<?> postMethodName(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        Recipes recipes = service.upload(id, file);
        return Res.renderJson(recipes, HttpStatus.OK, "Recipes Image Uploaded Successfully");
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RecipeUpdatesDTO req){
        Recipes recipes = service.update(id, req);
        return Res.renderJson(recipes, HttpStatus.OK, "Recipes Update Sucessfully");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }
}
