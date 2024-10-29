package com.example.rentproject.service;

import com.example.rentproject.model.MaintenanceModel;
import com.example.rentproject.model.PaymentModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public PaymentService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/payment";
        this.objectMapper = objectMapper;
    }

//    public List<PaymentModel> getAllPayments() {
//        ResponseEntity<PaymentModel[]> response = restTemplate.getForEntity(apiUrl, PaymentModel[].class);
//        return Arrays.asList(response.getBody());
//    }

    public List<PaymentModel> getAllPayments() {
        PaymentModel[] payments = restTemplate.getForObject(apiUrl, PaymentModel[].class);

        try {
            String json = objectMapper.writeValueAsString(payments);
            System.out.println("Response in JSON format: " + json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }

        return List.of(payments);
    }

    public PaymentModel getPaymentById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, PaymentModel.class);
    }

    public void savePayment(PaymentModel payment) {
        restTemplate.postForObject(apiUrl, payment, PaymentModel.class);
    }

    public void updatePayment(Long id, PaymentModel payment) {
        restTemplate.put(apiUrl + "/" + id, payment);
    }

    public void deletePayment(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}