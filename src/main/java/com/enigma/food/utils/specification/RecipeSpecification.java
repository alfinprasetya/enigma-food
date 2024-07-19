package com.enigma.food.utils.specification;

import com.enigma.food.model.Recipes;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RecipeSpecification {
    public static Specification<Recipes> getRecipeSpecification(String name, Integer price){
        return(root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (price != null) {
                Predicate pricePredicate = criteriaBuilder.equal(root.get("price"), price);
                predicates.add(pricePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
