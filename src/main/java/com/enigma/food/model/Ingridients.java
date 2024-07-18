package com.enigma.food.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingridients")
@Getter
@Setter
@Builder
public class Ingridients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private String recipe;
    @ManyToOne
    @JoinColumn(name = "item_id")
    private String item;
    private Integer qty;
}
