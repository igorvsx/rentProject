//package com.example.rentproject.controller;
//
//import com.example.rentproject.model.LocationModel;
//import com.example.rentproject.repository.LocationRepository;
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
//@RequestMapping("/manager/location")
//@PreAuthorize("hasAnyAuthority('MANAGER')")
//public class LocationController {
//    @Autowired
//    private LocationRepository locationRepository;
//
//    @GetMapping()
//    public String listLocations(Model model) {
//        model.addAttribute("locations", locationRepository.findAll());
//        return "manager/location/locationList";
//    }
//
//    @GetMapping("/add")
//    public String addLocationForm(Model model) {
//        model.addAttribute("location", new LocationModel());
//        return "manager/location/locationAdd";
//    }
//
//    @PostMapping("/add")
//    public String addLocation(@Valid @ModelAttribute LocationModel location, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", bindingResult.getAllErrors());
//            return "manager/location/locationAdd";
//        }
//
//        locationRepository.save(location);
//        return "redirect:/manager/location";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editLocationForm(@PathVariable Long id, Model model) {
//        LocationModel location = locationRepository.findById(id).orElseThrow();
//
//        model.addAttribute("location", location);
//        return "manager/location/locationForm";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editLocation(@PathVariable Long id, @Valid @ModelAttribute LocationModel updatedLocation, BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            model.addAttribute("message", result.getAllErrors());
//            return "manager/location/locationForm";
//        }
//        locationRepository.save(updatedLocation);
//        return "redirect:/manager/location";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteLocation(@PathVariable Long id, Model model) {
//        locationRepository.deleteById(id);
//        return "redirect:/manager/location";
//    }
//}
package com.example.rentproject.controller;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.model.LocationModel;
import com.example.rentproject.service.CarService;
import com.example.rentproject.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/location")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping()
    public String listLocations(Model model) {
        model.addAttribute("locations", locationService.getAllLocations());
        return "manager/location/locationList";
    }

    @GetMapping("/add")
    public String addLocationForm(Model model) {
        model.addAttribute("location", new LocationModel());
        return "manager/location/locationAdd";
    }

    @PostMapping("/add")
    public String addLocation(@Valid @ModelAttribute LocationModel location, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            return "manager/location/locationAdd";
        }

        locationService.saveLocation(location);
        return "redirect:/manager/location";
    }

    @GetMapping("/edit/{id}")
    public String editLocationForm(@PathVariable Long id, Model model) {
        LocationModel location = locationService.getLocationById(id);
        model.addAttribute("location", location);
        return "manager/location/locationForm";
    }

    @PostMapping("/edit/{id}")
    public String editLocation(@PathVariable Long id, @Valid @ModelAttribute LocationModel updatedLocation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            return "manager/location/locationForm";
        }
        locationService.updateLocation(id, updatedLocation);
        return "redirect:/manager/location";
    }

    @GetMapping("/delete/{id}")
    public String deleteLocation(@PathVariable Long id, Model model) {
        locationService.deleteLocation(id);
        return "redirect:/manager/location";
    }
}
