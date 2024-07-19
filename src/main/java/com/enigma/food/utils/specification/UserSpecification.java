package com.enigma.food.utils.specification;

import com.enigma.food.model.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class UserSpecification {

    public static Specification<User> getSpecification(
            String username,
            Integer minBalance,
            Integer maxBalance
    ) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (username != null && !username.isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("username"), "%" + username + "%"));
            }

            if (minBalance != null) {
                Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("balance"), minBalance);
                predicates.add(minPricePredicate);
            }

            if (maxBalance != null) {
                Predicate maxPricePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("balance"), maxBalance);
                predicates.add(maxPricePredicate);
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
