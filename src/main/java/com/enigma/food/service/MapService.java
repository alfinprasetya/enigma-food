package com.enigma.food.service;

import com.enigma.food.model.Coordinate;
import com.enigma.food.utils.dto.GetDistanceRequest;

public interface MapService {
  Coordinate getCityCoordinate(String city);

  Integer getDistance(GetDistanceRequest request);
  
  Integer getPriceByDistance(GetDistanceRequest request);
}
