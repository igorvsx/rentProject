package com.example.rentproject.contoller;

import com.example.rentproject.model.RoleEnum;
import com.example.rentproject.model.UserModel;
import com.example.rentproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }
        else if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(RoleEnum.CUSTOMER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/login";
    }
}
