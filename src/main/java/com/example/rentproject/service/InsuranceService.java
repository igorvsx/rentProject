package com.example.rentproject.service;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.model.InsuranceModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class InsuranceService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public InsuranceService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/insurance";
        this.objectMapper = objectMapper;
    }

    public List<InsuranceModel> getAllInsurances() {
        InsuranceModel[] insurances = restTemplate.getForObject(apiUrl, InsuranceModel[].class);

        try {
            String json = objectMapper.writeValueAsString(insurances);
            System.out.println("Response in JSON format: " + json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }

        return List.of(insurances);
    }

    public InsuranceModel getInsuranceById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, InsuranceModel.class);
    }

    public void saveInsurance(InsuranceModel insurance) {
        restTemplate.postForObject(apiUrl, insurance, InsuranceModel.class);
    }

    public void updateInsurance(Long id, InsuranceModel insurance) {
        restTemplate.put(apiUrl + "/" + id, insurance);
    }

    public void deleteInsurance(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}