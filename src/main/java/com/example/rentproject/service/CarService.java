package com.example.rentproject.service;

import com.example.rentproject.model.CarModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CarService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public CarService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/car";
    }

    public List<CarModel> getAllCars() {
        return List.of(restTemplate.getForObject(apiUrl, CarModel[].class));
    }

    public CarModel getCarById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, CarModel.class);
    }

    public void saveCar(CarModel car) {
        restTemplate.postForObject(apiUrl, car, CarModel.class);
    }

    public void updateCar(Long id, CarModel car) {
        restTemplate.put(apiUrl + "/" + id, car);
    }

    public void deleteCar(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}
