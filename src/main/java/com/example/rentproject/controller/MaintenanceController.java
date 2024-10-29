//package com.example.rentproject.controller;
//
//import com.example.rentproject.model.CarModel;
//import com.example.rentproject.model.MaintenanceModel;
//import com.example.rentproject.repository.CarRepository;
//import com.example.rentproject.repository.MaintenanceRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Controller
//@RequestMapping("/manager/maintenance")
//@PreAuthorize("hasAnyAuthority('MANAGER')")
//public class MaintenanceController {
//    @Autowired
//    private MaintenanceRepository maintenanceRepository;
//
//    @Autowired
//    private CarRepository carRepository;
//
//    @GetMapping()
//    public String listMaintenances(Model model) {
//        List<CarModel> cars = carRepository.findAll();
//        model.addAttribute("cars", cars);
//        model.addAttribute("maintenances", maintenanceRepository.findAll());
//        return "manager/maintenance/maintenanceList";
//    }
//
//    @GetMapping("/add")
//    public String addMaintenanceForm(Model model) {
//        List<CarModel> cars = carRepository.findAll();
//        model.addAttribute("cars", cars);
//        model.addAttribute("maintenance", new MaintenanceModel());
//        return "manager/maintenance/maintenanceAdd";
//    }
//
//    @PostMapping("/add")
//    public String addMaintenance(@Valid @ModelAttribute MaintenanceModel maintenance, BindingResult bindingResult, Model model) {
//        List<CarModel> cars = carRepository.findAll();
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", bindingResult.getAllErrors());
//            model.addAttribute("maintenance", new MaintenanceModel());
//            model.addAttribute("cars", cars);
//            return "manager/maintenance/maintenanceAdd";
//        }
//
//        maintenanceRepository.save(maintenance);
//        return "redirect:/manager/maintenance";
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String editMaintenanceForm(@PathVariable Long id, Model model) {
//        MaintenanceModel maintenance = maintenanceRepository.findById(id).orElseThrow();
//        List<CarModel> cars = carRepository.findAll();
//        model.addAttribute("cars", cars);
//        model.addAttribute("maintenance", maintenance);
//        return "manager/maintenance/maintenanceForm";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editMaintenance(@PathVariable Long id, @Valid @ModelAttribute MaintenanceModel updatedMaintenance, BindingResult result, Model model) {
//        MaintenanceModel existingMaintenance = maintenanceRepository.findById(id).orElseThrow();
//        List<CarModel> cars = carRepository.findAll();
//        if (result.hasErrors()) {
//            model.addAttribute("message", result.getAllErrors());
//            model.addAttribute("maintenance", existingMaintenance);
//            model.addAttribute("cars", cars);
//            return "manager/maintenance/maintenanceForm";
//        }
//
//        updatedMaintenance.setId(existingMaintenance.getId());
//        maintenanceRepository.save(updatedMaintenance);
//        return "redirect:/manager/maintenance";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteMaintenance(@PathVariable Long id, Model model) {
//        maintenanceRepository.deleteById(id);
//        return "redirect:/manager/maintenance";
//    }
//}

package com.example.rentproject.controller;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.model.MaintenanceModel;
import com.example.rentproject.service.CarService;
import com.example.rentproject.service.MaintenanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/maintenance")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class MaintenanceController {

    @Autowired
    private MaintenanceService maintenanceService;

    @Autowired
    private CarService carService;

    @GetMapping()
    public String listMaintenances(Model model) {
        model.addAttribute("maintenances", maintenanceService.getAllMaintenances());
        model.addAttribute("cars", carService.getAllCars());
        return "manager/maintenance/maintenanceList";
    }

    @GetMapping("/add")
    public String addMaintenanceForm(Model model) {
        model.addAttribute("maintenance", new MaintenanceModel());
        model.addAttribute("cars", carService.getAllCars());
        return "manager/maintenance/maintenanceAdd";
    }

    @PostMapping("/add")
    public String addMaintenance(@Valid @ModelAttribute("maintenance") MaintenanceModel maintenance,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("cars", carService.getAllCars());
            return "manager/maintenance/maintenanceAdd";
        }

        CarModel car = carService.getCarById(maintenance.getCar().getId());
        maintenance.setCar(car);

        maintenanceService.saveMaintenance(maintenance);
        return "redirect:/manager/maintenance";
    }

    @GetMapping("/edit/{id}")
    public String editMaintenanceForm(@PathVariable Long id, Model model) {
        model.addAttribute("maintenance", maintenanceService.getMaintenanceById(id));
        model.addAttribute("cars", carService.getAllCars());
        return "manager/maintenance/maintenanceForm";
    }

    @PostMapping("/edit/{id}")
    public String editMaintenance(@PathVariable Long id, @Valid @ModelAttribute MaintenanceModel updatedMaintenance,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("cars", carService.getAllCars());
            return "manager/maintenance/maintenanceForm";
        }

        // Установите ID для обновленного страхования
        updatedMaintenance.setId(id);

        // Получите автомобиль по ID и установите его в обновленное страхование
        CarModel car = carService.getCarById(updatedMaintenance.getCar().getId());
        updatedMaintenance.setCar(car);

        maintenanceService.updateMaintenance(id, updatedMaintenance);
        return "redirect:/manager/maintenance";
    }

    @GetMapping("/delete/{id}")
    public String deleteMaintenance(@PathVariable Long id) {
        maintenanceService.deleteMaintenance(id);
        return "redirect:/manager/maintenance";
    }
}
