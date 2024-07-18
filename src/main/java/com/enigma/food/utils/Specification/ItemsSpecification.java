package com.enigma.food.utils.Specification;

import com.enigma.food.model.Items;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ItemsSpecification {

    public static Specification<Items> getItemsSpecification(String name, Integer maxQty, Integer minQty){
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()){
                Predicate namePredicate = criteriaBuilder.like(root.get("name"), "%" + name + "%");
                predicates.add(namePredicate);
            }
            if (maxQty != null){
                Predicate maxQtyPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("qty"), maxQty);
                predicates.add(maxQtyPredicate);
            }
            if (minQty != null){
                Predicate minQtyPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("qty"), minQty);
                predicates.add(minQtyPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
