package com.example.rentproject.controller;

import com.example.rentproject.config.ApiConfig;
import com.example.rentproject.model.RoleEnum;
import com.example.rentproject.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/registration")
    public String regView() {
        return "registration";
    }

    @PostMapping("/registration")
    public String reg(@Valid UserModel user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", "Ошибка валидации: проверьте корректность введенных данных.");
            return "registration";
        }

        UserModel existingUser = restTemplate.getForObject(apiConfig.getApiUrl() + "/user/username/" + user.getUsername(), UserModel.class);
        if (existingUser != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }

        existingUser = restTemplate.getForObject(apiConfig.getApiUrl() + "/user/email/" + user.getEmail(), UserModel.class);
        if (existingUser != null) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.CUSTOMER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        restTemplate.postForObject(apiConfig.getApiUrl(), user, UserModel.class);
        return "redirect:/login";
    }
}
