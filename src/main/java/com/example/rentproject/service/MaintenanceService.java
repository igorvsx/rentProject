package com.example.rentproject.service;

import com.example.rentproject.model.FeedbackModel;
import com.example.rentproject.model.MaintenanceModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MaintenanceService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public MaintenanceService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/maintenance";
        this.objectMapper = objectMapper;
    }

//    public List<MaintenanceModel> getAllMaintenances() {
//        ResponseEntity<MaintenanceModel[]> response = restTemplate.getForEntity(apiUrl, MaintenanceModel[].class);
//        return Arrays.asList(response.getBody());
//    }

    public List<MaintenanceModel> getAllMaintenances() {
        MaintenanceModel[] maintenances = restTemplate.getForObject(apiUrl, MaintenanceModel[].class);

        try {
            String json = objectMapper.writeValueAsString(maintenances);
            System.out.println("Response in JSON format: " + json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }

        return List.of(maintenances);
    }

    public MaintenanceModel getMaintenanceById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, MaintenanceModel.class);
    }

    public void saveMaintenance(MaintenanceModel maintenance) {
        restTemplate.postForObject(apiUrl, maintenance, MaintenanceModel.class);
    }

    public void updateMaintenance(Long id, MaintenanceModel maintenance) {
        restTemplate.put(apiUrl + "/" + id, maintenance);
    }

    public void deleteMaintenance(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}