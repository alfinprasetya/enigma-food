package com.enigma.food.service.impl;

import com.enigma.food.model.Ingridients;
import com.enigma.food.repository.IngridientsRepository;
import com.enigma.food.service.IngridientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
@RequiredArgsConstructor
public class IngridientsServiceImpl implements IngridientService {
    private final IngridientsRepository ingridientsRepository;
    @Override
    public List<Ingridients> getAll() {
        return ingridientsRepository.findAll();
    }

    @Override
    public Ingridients getOne(Integer id) {
        return ingridientsRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Items With " + id + " Not Found"));
    }

    @Override
    public Ingridients create(Ingridients req) {
        return ingridientsRepository.save(req);
    }

    @Override
    public Ingridients update(Integer id, Ingridients req) {
        Ingridients ingridients = this.getOne(id);
        ingridients.setName(req.getName());
        ingridients.setName(req.getRecipe_id());
        ingridients.setQty(req.getQty());
        return ingridientsRepository.save(ingridients);
    }

    @Override
    public void delete(Integer id) {ingridientsRepository.deleteById(id);

    }
}
