package com.enigma.food.service;



import com.enigma.food.model.Ingridients;

import java.util.List;

public interface IngridientService {
    List<Ingridients> getAll();
    Ingridients getOne(Integer id);
    Ingridients create(Ingridients req);
    Ingridients update(Integer id, Ingridients req);
    void delete(Integer id);
}
