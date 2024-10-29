package com.example.rentproject.service;

import com.example.rentproject.model.LocationModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class LocationService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public LocationService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/location";
    }

    public List<LocationModel> getAllLocations() {
        ResponseEntity<LocationModel[]> response = restTemplate.getForEntity(apiUrl, LocationModel[].class);
        return Arrays.asList(response.getBody());
    }

    public LocationModel getLocationById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, LocationModel.class);
    }

    public void saveLocation(LocationModel location) {
        restTemplate.postForObject(apiUrl, location, LocationModel.class);
    }

    public void updateLocation(Long id, LocationModel location) {
        restTemplate.put(apiUrl + "/" + id, location);
    }

    public void deleteLocation(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}