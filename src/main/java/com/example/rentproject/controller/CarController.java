package com.example.rentproject.controller;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/car")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class CarController {
    @Autowired
    private CarRepository carRepository;

    @GetMapping()
    public String listCars(Model model) {
        model.addAttribute("cars", carRepository.findAll());
        return "manager/car/carList";
    }

    @GetMapping("/add")
    public String addCarForm(Model model) {
        model.addAttribute("car", new CarModel());
        return "manager/car/carAdd";
    }

    @PostMapping("/add")
    public String addCar(@Valid @ModelAttribute CarModel car, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return "manager/car/carAdd";
        }

        carRepository.save(car);
        return "redirect:/manager/car";
    }

    @GetMapping("/edit/{id}")
    public String editCarForm(@PathVariable Long id, Model model) {
        CarModel car = carRepository.findById(id).orElseThrow();

        model.addAttribute("car", car);
        return "manager/car/carForm";
    }

    @PostMapping("/edit/{id}")
    public String editCar(@PathVariable Long id, @Valid @ModelAttribute CarModel updatedCar, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            return "manager/car/carForm";
        }
        carRepository.save(updatedCar);
        return "redirect:/manager/car";
    }

    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id, Model model) {
        carRepository.deleteById(id);
        return "redirect:/manager/car";
    }
}
