//package com.example.rentproject.controller;
//
//import com.example.rentproject.model.RoleEnum;
//import com.example.rentproject.model.UserModel;
//import com.example.rentproject.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/admin")
//@PreAuthorize("hasAnyAuthority('ADMIN')")
//public class AdminController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping()
//    public String listUsers(Model model) {
//        model.addAttribute("users", userRepository.findAll());
//        return "admin/userList";
//    }
//
//    @GetMapping("/add")
//    public String addUserForm(Model model) {
//        model.addAttribute("user", new UserModel());
//        model.addAttribute("allRoles", RoleEnum.values());
//        return "admin/userAdd";
//    }
//
//    @PostMapping("/add")
//    public String addUser(@Valid @ModelAttribute UserModel user, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", bindingResult.getAllErrors());
//            model.addAttribute("user", new UserModel());
//            model.addAttribute("allRoles", RoleEnum.values());
//            return "admin/userAdd";
//        }
//
//        if (userRepository.existsByUsername(user.getUsername())) {
//            model.addAttribute("message", "Пользователь с таким логином уже существует");
//            model.addAttribute("user", new UserModel());
//            model.addAttribute("allRoles", RoleEnum.values());
//            return "admin/userAdd";
//        }
//
//        if (userRepository.existsByEmail(user.getEmail())) {
//            model.addAttribute("message", "Пользователь с таким email уже существует");
//            model.addAttribute("user", new UserModel());
//            model.addAttribute("allRoles", RoleEnum.values());
//            return "admin/userAdd";
//        }
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editUserForm(@PathVariable Long id, Model model) {
//        UserModel user = userRepository.findById(id).orElseThrow();
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", RoleEnum.values());
//        return "admin/userForm";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editUser(@PathVariable Long id, @Valid @ModelAttribute UserModel updatedUser, BindingResult result, Model model) {
//        UserModel existingUser = userRepository.findById(id).orElseThrow();
//
//        if (result.hasErrors()) {
//            model.addAttribute("message", result.getAllErrors());
//            model.addAttribute("user", existingUser);
//            model.addAttribute("allRoles", RoleEnum.values());
//            return "admin/userForm";
//        }
//
//        if (!updatedUser.getPassword().isEmpty()) {
//            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
//        }
//
//        existingUser.setUserId(existingUser.getUserId());
//        existingUser.setUsername(updatedUser.getUsername());
//        existingUser.setEmail(updatedUser.getEmail());
//        existingUser.setRoles(updatedUser.getRoles());
//        existingUser.setActive(updatedUser.isActive());
//
//        userRepository.save(existingUser);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable Long id, Model model) {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String currentUsername;
//        if (principal instanceof UserDetails) {
//            currentUsername = ((UserDetails) principal).getUsername();
//        } else {
//            currentUsername = principal.toString();
//        }
//
//        UserModel userToDelete = userRepository.findById(id).orElseThrow();
//
//        if (userToDelete.getUsername().equals(currentUsername)) {
//            model.addAttribute("message", "Нельзя удалить текущего пользователя");
//            model.addAttribute("users", userRepository.findAll());
//            return "admin/userList";
//        }
//
//        userRepository.deleteById(id);
//        return "redirect:/admin";
//    }
//}

package com.example.rentproject.controller;

import com.example.rentproject.model.RoleEnum;
import com.example.rentproject.model.UserModel;
import com.example.rentproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/userList";
    }

    @GetMapping("/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new UserModel());
        model.addAttribute("allRoles", RoleEnum.values());
        return "admin/userAdd";
    }

    @PostMapping("/add")
    public String addUser(@Valid @ModelAttribute UserModel user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("allRoles", RoleEnum.values());
            return "admin/userAdd";
        }

        if (userService.getUserByUsername(user.getUsername()) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            model.addAttribute("allRoles", RoleEnum.values());
            return "admin/userAdd";
        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            model.addAttribute("message", "Пользователь с таким email уже существует");
            model.addAttribute("allRoles", RoleEnum.values());
            return "admin/userAdd";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", RoleEnum.values());
        return "admin/userForm";
    }

    @PostMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, @Valid @ModelAttribute UserModel updatedUser, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("allRoles", RoleEnum.values());
            return "admin/userForm";
        }

        updatedUser.setUserId(id);
        if (!updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        userService.updateUser(id, updatedUser);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}


