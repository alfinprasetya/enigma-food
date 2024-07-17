package com.enigma.food.utils.dto;

import com.enigma.food.model.Coordinate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetDistanceRequest {
  private Coordinate origin;
  private Coordinate destination;
}
