package com.enigma.food.controller;

import com.enigma.food.model.Ingridients;
import com.enigma.food.service.IngridientService;
import com.enigma.food.utils.Res;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingridients")
@RequiredArgsConstructor
public class IngridientsController {
    private final IngridientService ingridientService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return Res.renderJson(
                ingridientService.getAll(),
                HttpStatus.OK,
                "Succes Get all Items"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                ingridientService.getOne(id),
                HttpStatus.OK,
                "Succes Get Items " + id
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Ingridients req){
        return Res.renderJson(
                ingridientService.create(req),
                HttpStatus.CREATED,
                "Succes Create New Item"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Ingridients req){
        return Res.renderJson(
                ingridientService.update(id, req),
                HttpStatus.OK,
                "Succes Update Items " + id
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        ingridientService.delete(id);
    }
}
