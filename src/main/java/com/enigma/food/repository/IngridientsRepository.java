package com.enigma.food.repository;

import com.enigma.food.model.Ingridients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngridientsRepository extends JpaRepository<Ingridients, Integer> {
}
