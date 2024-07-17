package com.enigma.food.service;

import com.enigma.food.model.Coordinate;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MapService {
  Coordinate getCityCoordinate(String city) throws JsonProcessingException;

  Integer getDistance(GetDistanceRequest request) throws JsonProcessingException;
  
  Integer getPriceByDistance(GetDistanceRequest request) throws JsonProcessingException;
}
