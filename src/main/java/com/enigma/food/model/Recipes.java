package com.enigma.food.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "nama_table")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Recipes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String method;
}
