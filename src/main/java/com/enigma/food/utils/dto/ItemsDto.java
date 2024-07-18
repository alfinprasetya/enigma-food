package com.enigma.food.utils.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemsDto {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 200, min =2)
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Name must contain only letters and spaces")
    private String name;

    @NotNull(message = "Quantity is mandatory")
    @PositiveOrZero(message = "Quantity must be positive")
    private Integer qty;
}
