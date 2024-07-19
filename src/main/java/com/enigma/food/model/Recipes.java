package com.enigma.food.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();
}
