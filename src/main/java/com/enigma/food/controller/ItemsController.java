package com.enigma.food.controller;

import com.enigma.food.model.Items;
import com.enigma.food.service.ItemsService;
import com.enigma.food.utils.Res;

import com.enigma.food.utils.dto.ItemsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemsController {
    private final ItemsService itemsService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        return Res.renderJson(
                itemsService.getAll(),
                HttpStatus.OK,
                "Succes Get all Items"
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(
                itemsService.getOne(id),
                HttpStatus.OK,
                "Succes Get Items " + id
        );
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ItemsDto req){
        return Res.renderJson(
                itemsService.create(req),
                HttpStatus.CREATED,
                "Succes Create New Item"
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ItemsDto req){
        return Res.renderJson(
                itemsService.update(id, req),
                HttpStatus.OK,
                "Succes Update Items " + id
        );
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        itemsService.delete(id);
    }
}
