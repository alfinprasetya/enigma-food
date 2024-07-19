package com.enigma.food.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enigma.food.model.Recipes;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer>, JpaSpecificationExecutor<Recipes> {
}
