package com.enigma.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.food.model.Items;

@Repository
public interface ItemsRepo extends JpaRepository<Items, Integer> {
}
