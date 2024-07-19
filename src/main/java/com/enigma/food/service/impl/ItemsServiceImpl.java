package com.enigma.food.service.impl;

import com.enigma.food.model.Items;
import com.enigma.food.repository.ItemsRepo;
import com.enigma.food.service.ItemsService;
import com.enigma.food.service.ValidationService;
import com.enigma.food.utils.dto.ItemsCreateDto;
import com.enigma.food.utils.dto.ItemsUpdateDto;
import com.enigma.food.utils.specification.ItemsSpecification;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ItemsServiceImpl implements ItemsService {
    private final ItemsRepo itemsRepo;
    private final ValidationService validationService;

    @Override
    public Page<Items> getAll(String name, Integer maxQty, Integer minQty, Pageable pageable) {
        Specification<Items> spec = ItemsSpecification.getItemsSpecification(name, maxQty, minQty );
        return itemsRepo.findAll(spec, pageable);
    }

    @Override
    public Items getOne(Integer id) {
        return itemsRepo.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Items With " + id + " Not Found"));
    }

    @Override
    public Items create(ItemsCreateDto req) {
        validationService.validate(req);
        Items items = new Items();
        items.setName(req.getName());
        items.setQty(req.getQty());
        return itemsRepo.save(items);
    }

    @Override
    public Items update(Integer id, ItemsUpdateDto req){
        validationService.validate(req);
        Items items = this.getOne(id);

        if (req.getName() != null){
            items.setName(req.getName());
        }
        if (req.getQty() != null){
            items.setQty(req.getQty());
        }
        return itemsRepo.save(items);
    }

    @Override
    public void delete(Integer id) {
        itemsRepo.deleteById(id);
    }
}
