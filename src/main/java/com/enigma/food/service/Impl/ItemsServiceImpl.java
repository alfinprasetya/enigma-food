package com.enigma.food.service.Impl;

import com.enigma.food.Model.Items;
import com.enigma.food.Repository.ItemsRepo;
import com.enigma.food.service.ItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {
    private final ItemsRepo itemsRepo;

    @Override
    public List<Items> getAll() {
        return itemsRepo.findAll();
    }

    @Override
    public Items getOne(Integer id) {
        return itemsRepo.findById(id).orElseThrow(()-> new RuntimeException("Items With " + id + " Not Found"));
    }

    @Override
    public Items create(Items req) {
        return itemsRepo.save(req);
    }

    @Override
    public Items update(Integer id, Items req) {
        Items items = this.getOne(id);
        items.setName(req.getName());
        items.setQty(req.getQty());
        return itemsRepo.save(items);
    }

    @Override
    public void delete(Integer id) {
        itemsRepo.deleteById(id);
    }
}
