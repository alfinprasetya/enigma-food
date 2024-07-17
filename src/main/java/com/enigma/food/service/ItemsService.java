package com.enigma.food.service;

import com.enigma.food.Model.Items;

import java.util.List;

public interface ItemsService {
    List<Items> getAll();
    Items getOne(Integer id);
    Items create(Items req);
    Items update(Integer id, Items req);
    void delete(Integer id);
}
