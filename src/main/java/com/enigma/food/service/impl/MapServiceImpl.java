package com.enigma.food.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.enigma.food.model.Coordinate;
import com.enigma.food.service.MapService;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapServiceImpl implements MapService {

  private static final RestTemplate restTemplate = new RestTemplate();

  @Value("${app.mapboxToken}")
  private String accessToken;

  @Override
  public Coordinate getCityCoordinate(String city) {
    String url = String.format(
        "https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?limit=1&access_token=%s",
        city, accessToken);

    String response = null;

    try {
      response = restTemplate.getForObject(url, String.class);
    } catch (HttpClientErrorException e) {
      throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
    }

    Coordinate realCoordinate = new Coordinate();

    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response);
      JsonNode coordinates = root.path("features").get(0).path("geometry").path("coordinates");

      double longitude = coordinates.get(0).asDouble();
      double latitude = coordinates.get(1).asDouble();

      realCoordinate.setLatitude(latitude);
      realCoordinate.setLongitude(longitude);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Coordinate not found");
    }

    return realCoordinate;
  }

  @Override
  public Integer getDistance(GetDistanceRequest request) {
    String url = String.format(
        "https://api.mapbox.com/directions/v5/mapbox/driving/%f,%f;%f,%f?access_token=%s",
        request.getOrigin().getLongitude(),
        request.getOrigin().getLatitude(),
        request.getDestination().getLongitude(),
        request.getDestination().getLatitude(),
        accessToken);

    String response = null;

    try {
      response = restTemplate.getForObject(url, String.class);
    } catch (HttpClientErrorException e) {
      throw new ResponseStatusException(e.getStatusCode(), e.getMessage());
    } 

    Integer realDistance = 0;

    try {
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response);
      JsonNode distance = root.path("routes").get(0).path("distance");
      realDistance = distance.asInt(0);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Distance can't be calculated");
    }

    return realDistance / 1000;
  }

  @Override
  public Integer getPriceByDistance(GetDistanceRequest request) {
    Integer distance = this.getDistance(request);

    return distance * 1000;
  }

}
