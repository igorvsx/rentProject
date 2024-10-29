package com.example.rentproject.service;

import com.example.rentproject.model.MaintenanceModel;
import com.example.rentproject.model.RentalModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class RentalService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public RentalService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/rental";
        this.objectMapper = objectMapper;
    }

//    public List<RentalModel> getAllRentals() {
//        ResponseEntity<RentalModel[]> response = restTemplate.getForEntity(apiUrl, RentalModel[].class);
//        return Arrays.asList(response.getBody());
//    }

    public List<RentalModel> getAllRentals() {
        RentalModel[] rentals = restTemplate.getForObject(apiUrl, RentalModel[].class);

        try {
            String json = objectMapper.writeValueAsString(rentals);
            System.out.println("Response in JSON format: " + json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }

        return List.of(rentals);
    }

    public RentalModel getRentalById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, RentalModel.class);
    }

    public void saveRental(RentalModel rental) {
        restTemplate.postForObject(apiUrl, rental, RentalModel.class);
    }

    public void updateRental(Long id, RentalModel rental) {
        restTemplate.put(apiUrl + "/" + id, rental);
    }

    public void deleteRental(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}