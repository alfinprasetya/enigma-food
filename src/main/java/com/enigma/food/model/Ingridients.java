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
    private String name;
    private String recipe_id;
    private Integer qty;
}
