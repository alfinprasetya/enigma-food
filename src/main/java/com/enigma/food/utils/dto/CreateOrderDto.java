package com.enigma.food.utils.dto;

import java.util.List;

import com.enigma.food.model.Coordinate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class CreateOrderDto {

  @Valid
  private Coordinate destination;

  @Valid
  private List<Recipe> recipes;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Recipe {

    @NotNull
    @Positive
    private Integer recipeId;

    @NotNull
    @Positive
    private Integer qty;
  }
}
