package com.enigma.food.service;

import java.util.List;

import com.enigma.food.model.Items;

import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.ItemsUpdateDto;

public interface ItemsService {
    List<Items> getAll(String name, Integer maxQty, Integer minQty);
    Items getOne(Integer id);

    Items create(ItemsCreateDto req);
    Items update(Integer id, ItemsUpdateDto req);

    void delete(Integer id);
}
