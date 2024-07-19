package com.enigma.food.utils.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecipeUpdatesDTO {
    @Size(max = 110, min = 2)
    private String name;

    private String description;

    private String method;

    @Positive
    private Integer price;
}
