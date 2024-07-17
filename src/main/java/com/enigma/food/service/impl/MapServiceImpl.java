package com.enigma.food.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.enigma.food.model.Coordinate;
import com.enigma.food.service.MapService;
import com.enigma.food.utils.dto.GetDistanceRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MapServiceImpl implements MapService {

  private static final RestTemplate restTemplate = new RestTemplate();

  @Value("${app.mapboxToken}")
  private String accessToken;

  @Override
  public Coordinate getCityCoordinate(String city) throws JsonProcessingException {
    String url = String.format(
        "https://api.mapbox.com/geocoding/v5/mapbox.places/%s.json?limit=1&access_token=%s",
        city, accessToken);

    String response = restTemplate.getForObject(url, String.class);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response);
    JsonNode coordinates = root.path("features").get(0).path("geometry").path("coordinates");

    double longitude = coordinates.get(0).asDouble();
    double latitude = coordinates.get(1).asDouble();

    return Coordinate.builder().latitude(latitude).longitude(longitude).build();
  }

  @Override
  public Integer getDistance(GetDistanceRequest request) throws JsonProcessingException {
    String url = String.format(
        "https://api.mapbox.com/directions/v5/mapbox/driving/%f,%f;%f,%f?access_token=%s",
        request.getOrigin().getLongitude(),
        request.getOrigin().getLatitude(),
        request.getDestination().getLongitude(),
        request.getDestination().getLatitude(),
        accessToken);
    String response = restTemplate.getForObject(url, String.class);

    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(response);
    JsonNode distance = root.path("routes").get(0).path("distance");

    return distance.asInt(0) / 1000;
  }

}
