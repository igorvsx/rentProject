package com.example.rentproject.service;

import com.example.rentproject.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Arrays;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final String apiUrl;

    public UserService(RestTemplate restTemplate, @Value("${api.url}") String apiUrl) {
        this.restTemplate = restTemplate;
        this.apiUrl = apiUrl + "/user";
    }

    public List<UserModel> getAllUsers() {
        UserModel[] users = restTemplate.getForObject(apiUrl, UserModel[].class);
        return Arrays.asList(users);
    }

    public UserModel getUserById(Long id) {
        return restTemplate.getForObject(apiUrl + "/" + id, UserModel.class);
    }

    public UserModel getUserByUsername(String username) {
        return restTemplate.getForObject(apiUrl + "/username/" + username, UserModel.class);
    }

    public UserModel getUserByEmail(String email) {
        return restTemplate.getForObject(apiUrl + "/email/" + email, UserModel.class);
    }

    public void saveUser(UserModel user) {
        restTemplate.postForObject(apiUrl, user, UserModel.class);
    }

    public void updateUser(Long id, UserModel user) {
        restTemplate.put(apiUrl + "/" + id, user);
    }

    public void deleteUser(Long id) {
        restTemplate.delete(apiUrl + "/" + id);
    }
}