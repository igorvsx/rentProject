//package com.example.rentproject.controller;
//
//import com.example.rentproject.model.*;
//import com.example.rentproject.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@Controller
//@RequestMapping("/manager/rental")
//@PreAuthorize("hasAnyAuthority('MANAGER')")
//public class RentalController {
//
//    @Autowired
//    private RentalRepository rentalRepository;
//    @Autowired
//    private CarRepository carRepository;
//    @Autowired
//    private LocationRepository locationRepository;
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping()
//    public String listRentals(Model model) {
//        model.addAttribute("cars", carRepository.findAll());
//        model.addAttribute("locations", locationRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
//        model.addAttribute("rentals", rentalRepository.findAll());
//        return "manager/rental/rentalList";
//    }
//
//    @GetMapping("/add")
//    public String addRentalForm(Model model) {
//        model.addAttribute("cars", carRepository.findAll());
//        model.addAttribute("locations", locationRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
//        model.addAttribute("rental", new RentalModel());
//        return "manager/rental/rentalAdd";
//    }
//
//    @PostMapping("/add")
//    public String addRental(@Valid @ModelAttribute RentalModel rental, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", bindingResult.getAllErrors());
//            model.addAttribute("rental", new RentalModel());
//            model.addAttribute("cars", carRepository.findAll());
//            model.addAttribute("locations", locationRepository.findAll());
//            model.addAttribute("users", userRepository.findAll());
//            return "manager/rental/rentalAdd";
//        }
//
//        rentalRepository.save(rental);
//        return "redirect:/manager/rental";
//    }
//
//
//    @GetMapping("/edit/{id}")
//    public String editRentalForm(@PathVariable Long id, Model model) {
//        RentalModel rental = rentalRepository.findById(id).orElseThrow();
//        model.addAttribute("cars", carRepository.findAll());
//        model.addAttribute("locations", locationRepository.findAll());
//        model.addAttribute("users", userRepository.findAll());
//        model.addAttribute("rental", rental);
//        return "manager/rental/rentalForm";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editRental(@PathVariable Long id, @Valid @ModelAttribute RentalModel updatedRental, BindingResult result, Model model) {
//        RentalModel existingRental = rentalRepository.findById(id).orElseThrow();
//        if (result.hasErrors()) {
//            model.addAttribute("message", result.getAllErrors());
//            model.addAttribute("rental", existingRental);
//            model.addAttribute("cars", carRepository.findAll());
//            model.addAttribute("locations", locationRepository.findAll());
//            model.addAttribute("users", userRepository.findAll());
//            return "manager/rental/rentalForm";
//        }
//
//        updatedRental.setId(existingRental.getId());
//        rentalRepository.save(updatedRental);
//        return "redirect:/manager/rental";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteRental(@PathVariable Long id, Model model) {
//        rentalRepository.deleteById(id);
//        return "redirect:/manager/rental";
//    }
//}

package com.example.rentproject.controller;

import com.example.rentproject.model.*;
import com.example.rentproject.service.CarService;
import com.example.rentproject.service.LocationService;
import com.example.rentproject.service.RentalService;
import com.example.rentproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/rental")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class RentalController {

    @Autowired
    private RentalService rentalService;
    @Autowired
    private CarService carService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public String listRentals(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("rentals", rentalService.getAllRentals());
        return "manager/rental/rentalList";
    }

    @GetMapping("/add")
    public String addRentalForm(Model model) {
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("rental", new RentalModel());
        return "manager/rental/rentalAdd";
    }

    @PostMapping("/add")
    public String addRental(@Valid @ModelAttribute("rental") RentalModel rental, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("rental", new RentalModel());
            model.addAttribute("cars", carService.getAllCars());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("users", userService.getAllUsers());
            return "manager/rental/rentalAdd";
        }

        CarModel car = carService.getCarById(rental.getCar().getId());
        LocationModel location = locationService.getLocationById(rental.getLocation().getId());
        UserModel user = userService.getUserById(rental.getUser().getUserId());
        rental.setCar(car);
        rental.setLocation(location);
        rental.setUser(user);

        rentalService.saveRental(rental);
        return "redirect:/manager/rental";
    }


    @GetMapping("/edit/{id}")
    public String editRentalForm(@PathVariable Long id, Model model) {
        RentalModel rental = rentalService.getRentalById(id);
        model.addAttribute("cars", carService.getAllCars());
        model.addAttribute("locations", locationService.getAllLocations());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("rental", rental);
        return "manager/rental/rentalForm";
    }

    @PostMapping("/edit/{id}")
    public String editRental(@PathVariable Long id, @Valid @ModelAttribute RentalModel updatedRental, BindingResult result, Model model) {
        RentalModel existingRental = rentalService.getRentalById(id);
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("rental", existingRental);
            model.addAttribute("cars", carService.getAllCars());
            model.addAttribute("locations", locationService.getAllLocations());
            model.addAttribute("users", userService.getAllUsers());
            return "manager/rental/rentalForm";
        }
        updatedRental.setId(id);

        CarModel car = carService.getCarById(updatedRental.getCar().getId());
        LocationModel location = locationService.getLocationById(updatedRental.getLocation().getId());
        UserModel user = userService.getUserById(updatedRental.getUser().getUserId());
        updatedRental.setCar(car);
        updatedRental.setLocation(location);
        updatedRental.setUser(user);

        rentalService.saveRental(updatedRental);
        return "redirect:/manager/rental";
    }

    @GetMapping("/delete/{id}")
    public String deleteRental(@PathVariable Long id, Model model) {
        rentalService.deleteRental(id);
        return "redirect:/manager/rental";
    }
}

