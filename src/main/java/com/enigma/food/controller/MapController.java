package com.enigma.food.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.enigma.food.model.Coordinate;
import com.enigma.food.service.MapService;
import com.enigma.food.utils.Res;
import com.enigma.food.utils.WebResponse;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {

  private final MapService mapService;
  

  @GetMapping("/{city}")
  public ResponseEntity<?> getCityCoordinat(@PathVariable String city) throws JsonProcessingException {
    return Res.renderJson(this.mapService.getCityCoordinate(city), HttpStatus.OK, "Coordinate found");
  }

  @PostMapping("/distance")
  public ResponseEntity<?> getDistance(@RequestBody GetDistanceRequest request) throws JsonProcessingException {
    return Res.renderJson(this.getDistance(request), HttpStatus.OK, "Distance calculated");
  }

}
