package com.enigma.food.service;

import java.util.List;

import com.enigma.food.model.Items;
import com.enigma.food.utils.dto.ItemsDto;

public interface ItemsService {
    List<Items> getAll();
    Items getOne(Integer id);
    Items create(ItemsDto req);
    Items update(Integer id, ItemsDto req);
    void delete(Integer id);
}
