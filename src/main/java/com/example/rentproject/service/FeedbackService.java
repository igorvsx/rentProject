package com.example.rentproject.service;

import com.example.rentproject.model.FeedbackModel;
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
public class FeedbackService {

    private final RestTemplate restTemplate;
    private final String apiUrl;
    private final ObjectMapper objectMapper;

    public FeedbackService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/feedback";
        this.objectMapper = objectMapper;
    }

//    public List<FeedbackModel> getAllFeedbacks() {
//        return List.of(restTemplate.getForObject(apiUrl, FeedbackModel[].class));
//    }
    public List<FeedbackModel> getAllFeedbacks() {
        FeedbackModel[] feedbacks = restTemplate.getForObject(apiUrl, FeedbackModel[].class);

        try {
            String json = objectMapper.writeValueAsString(feedbacks);
            System.out.println("Response in JSON format: " + json);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting to JSON: " + e.getMessage());
        }

        return List.of(feedbacks);
    }

    public FeedbackModel getFeedbackById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, FeedbackModel.class);
    }

    public void saveFeedback(FeedbackModel feedback) {
        restTemplate.postForObject(apiUrl, feedback, FeedbackModel.class);
    }

    public void updateFeedback(Long id, FeedbackModel feedback) {
        restTemplate.put(apiUrl + "/" + id, feedback);
    }

    public void deleteFeedback(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}