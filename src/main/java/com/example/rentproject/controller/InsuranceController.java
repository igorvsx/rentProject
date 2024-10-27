package com.example.rentproject.controller;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.model.InsuranceModel;
import com.example.rentproject.repository.CarRepository;
import com.example.rentproject.repository.InsuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/manager/insurance")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class InsuranceController {

    @Autowired
    private InsuranceRepository insuranceRepository;

    @Autowired
    private CarRepository carRepository;

    @GetMapping()
    public String listInsurances(Model model) {
        List<CarModel> cars = carRepository.findAll();
        model.addAttribute("insurances", insuranceRepository.findAll());
        model.addAttribute("cars", cars);
        return "manager/insurance/insuranceList";
    }

    @GetMapping("/add")
    public String addInsuranceForm(Model model) {
        List<CarModel> availableCars = carRepository.findAll()
                .stream()
                .filter(car -> insuranceRepository.findByCar(car).isEmpty()) // фильтр только для доступных автомобилей
                .collect(Collectors.toList());
        model.addAttribute("insurance", new InsuranceModel());
        model.addAttribute("cars", availableCars);
        return "manager/insurance/insuranceAdd";
    }

    @PostMapping("/add")
    public String addInsurance(@Valid @ModelAttribute InsuranceModel insurance, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            List<CarModel> availableCars = carRepository.findAll()
                    .stream()
                    .filter(car -> insuranceRepository.findByCar(car).isEmpty())
                    .collect(Collectors.toList());
            model.addAttribute("cars", availableCars);
            return "manager/insurance/insuranceAdd";
        }

        insuranceRepository.save(insurance);
        return "redirect:/manager/insurance";
    }

    @GetMapping("/edit/{id}")
    public String editInsuranceForm(@PathVariable Long id, Model model) {
        InsuranceModel insurance = insuranceRepository.findById(id).orElseThrow();

        List<CarModel> availableCars = carRepository.findAll()
                .stream()
                .filter(car -> insuranceRepository.findByCar(car).isEmpty() || car.equals(insurance.getCar()))
                .collect(Collectors.toList());

        model.addAttribute("insurance", insurance);
        model.addAttribute("cars", availableCars);
        return "manager/insurance/insuranceForm";
    }

    @PostMapping("/edit/{id}")
    public String editInsurance(@PathVariable Long id, @Valid @ModelAttribute InsuranceModel updatedInsurance, BindingResult result, Model model) {
        InsuranceModel existingInsurance = insuranceRepository.findById(id).orElseThrow();

        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            List<CarModel> availableCars = carRepository.findAll()
                    .stream()
                    .filter(car -> insuranceRepository.findByCar(car).isEmpty() || car.equals(existingInsurance.getCar()))
                    .collect(Collectors.toList());
            model.addAttribute("cars", availableCars);
            return "manager/insurance/insuranceForm";
        }

        insuranceRepository.save(updatedInsurance);
        return "redirect:/manager/insurance";
    }

    @GetMapping("/delete/{id}")
    public String deleteInsurance(@PathVariable Long id, Model model) {
        insuranceRepository.deleteById(id);
        return "redirect:/manager/insurance";
    }
}
