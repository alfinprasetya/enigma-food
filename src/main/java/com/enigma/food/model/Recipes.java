package com.enigma.food.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recipes")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String method;
    private Integer price;
    private String imageUrl;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingridients> ingridients = new ArrayList<>();

    public void addIngredients(Ingridients ingridients) {
        this.ingridients.add(ingridients);
        ingridients.setRecipe(this);
    }
}
