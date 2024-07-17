package com.enigma.food.service;

import java.util.List;

import com.enigma.food.model.Items;

public interface ItemsService {
    List<Items> getAll();
    Items getOne(Integer id);
    Items create(Items req);
    Items update(Integer id, Items req);
    void delete(Integer id);
}
