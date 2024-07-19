package com.enigma.food.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.enigma.food.model.Items;

import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.ItemsUpdateDto;

public interface ItemsService {
    Page<Items> getAll(String name, Integer maxQty, Integer minQty, Pageable pageable);
    Items getOne(Integer id);

    Items create(ItemsCreateDto req);
    Items update(Integer id, ItemsUpdateDto req);

    void delete(Integer id);
}
