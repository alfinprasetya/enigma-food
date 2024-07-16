package com.enigma.food.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enigma.food.model.Recipes;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipesRepository extends JpaRepository<Recipes, Integer> {
}
