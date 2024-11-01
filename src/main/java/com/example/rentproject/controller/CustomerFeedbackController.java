//package com.example.rentproject.controller;
//
//import com.example.rentproject.model.*;
//import com.example.rentproject.repository.FeedbackRepository;
//import com.example.rentproject.repository.RentalRepository;
//import com.example.rentproject.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/customer/feedback")
//@PreAuthorize("hasAnyAuthority('CUSTOMER')")
//public class CustomerFeedBackController {
//    @Autowired
//    private FeedbackRepository feedBackRepository;
//
//    @Autowired
//    private RentalRepository rentalRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private Long getCurrentUserId() {
//        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();
//        UserModel currentUser = userRepository.findByUsername(currentUsername);
//        System.out.println(currentUser.getUserId());
//        return currentUser.getUserId();
//    }
//
//    @GetMapping()
//    public String listFeedbacks(Model model) {
//        if (getCurrentUserId() != null) {
//            List<RentalModel> rentals = rentalRepository.findByUserUserId(getCurrentUserId());
//            model.addAttribute("rentals", rentals);
//            model.addAttribute("feedbacks", feedBackRepository.findByRentalUserId(getCurrentUserId()));
//        }
//        return "customer/feedback/feedbackList";
//    }
//
//    @GetMapping("/add")
//    public String addFeedbackForm(Model model) {
//        List<RentalModel> allRentals = rentalRepository.findByUserUserId(getCurrentUserId());
//        List<RentalModel> rentalsWithoutFeedback = allRentals.stream()
//                .filter(rental -> !feedBackRepository.existsByRentalId(rental.getId()))
//                .collect(Collectors.toList());
//
//        model.addAttribute("feedback", new FeedbackModel());
//        model.addAttribute("rentals", rentalsWithoutFeedback);
//        return "customer/feedback/feedbackAdd";
//    }
//
//    @ModelAttribute("feedback")
//    public FeedbackModel addFeedbackDate(FeedbackModel feedback) {
//        if (feedback.getFeedbackDate() == null) {
//            feedback.setFeedbackDate(LocalDate.now());
//        }
//        return feedback;
//    }
//
//    @PostMapping("/add")
//    public String addFeedback(@Valid @ModelAttribute("feedback") FeedbackModel feedback, BindingResult bindingResult, Model model) {
//        List<RentalModel> allRentals = rentalRepository.findByUserUserId(getCurrentUserId());
//        List<RentalModel> rentalsWithoutFeedback = allRentals.stream()
//                .filter(rental -> !feedBackRepository.existsByRentalId(rental.getId()))
//                .collect(Collectors.toList());
//
//        if (bindingResult.hasErrors()) {
//            model.addAttribute("message", bindingResult.getAllErrors());
//            model.addAttribute("feedback", new FeedbackModel());
//            model.addAttribute("rentals", rentalsWithoutFeedback);
//            return "customer/feedback/feedbackAdd";
//        }
//
//        feedBackRepository.save(feedback);
//        return "redirect:/customer/feedback";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editFeedbackForm(@PathVariable Long id, Model model) {
//        FeedbackModel feedback = feedBackRepository.findById(id).orElseThrow();
//        Long currentUserId = getCurrentUserId();
//        if (currentUserId != null) {
//            List<RentalModel> rentals = rentalRepository.findByUserUserId(currentUserId);
//
//            List<FeedbackModel> feedbacks = feedBackRepository.findByRentalUserId(currentUserId);
//
//            Set<Long> rentalIdsWithFeedback = feedbacks.stream()
//                    .map(f -> f.getRental().getId())
//                    .collect(Collectors.toSet());
//
//            rentals.removeIf(rental -> rentalIdsWithFeedback.contains(rental.getId())
//                    && !rental.getId().equals(feedback.getRental().getId()));
//
//            model.addAttribute("rentals", rentals);
//        }
//        model.addAttribute("feedback", feedback);
//        return "customer/feedback/feedbackForm";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String editFeedback(@PathVariable Long id, @Valid @ModelAttribute FeedbackModel updatedFeedback, BindingResult result, Model model) {
//        FeedbackModel existingFeedback = feedBackRepository.findById(id).orElseThrow();
//        Long currentUserId = getCurrentUserId();
//        List<RentalModel> rentals = rentalRepository.findByUserUserId(currentUserId);
//        if (result.hasErrors()) {
//            model.addAttribute("message", result.getAllErrors());
//
//            List<FeedbackModel> feedbacks = feedBackRepository.findByRentalUserId(currentUserId);
//            Set<Long> rentalIdsWithFeedback = feedbacks.stream()
//                    .map(f -> f.getRental().getId())
//                    .collect(Collectors.toSet());
//            rentals.removeIf(rental -> rentalIdsWithFeedback.contains(rental.getId())
//                    && !rental.getId().equals(existingFeedback.getRental().getId()));
//
//            model.addAttribute("feedback", existingFeedback);
//            model.addAttribute("rentals", rentals);
//            return "customer/feedback/feedbackForm";
//        }
//
//        updatedFeedback.setId(existingFeedback.getId());
//        updatedFeedback.setFeedbackDate(existingFeedback.getFeedbackDate());
//        feedBackRepository.save(updatedFeedback);
//
//        return "redirect:/customer/feedback";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteFeedback(@PathVariable Long id, Model model) {
//        feedBackRepository.deleteById(id);
//        return "redirect:/customer/feedback";
//    }
//
//}

package com.example.rentproject.controller;

import com.example.rentproject.model.FeedbackModel;
import com.example.rentproject.model.RentalModel;
import com.example.rentproject.service.FeedbackService;
import com.example.rentproject.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.events.Event;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@RequestMapping("/customer/feedback")
@PreAuthorize("hasAnyAuthority('CUSTOMER')")
public class CustomerFeedbackController {

    private final FeedbackService feedbackService;
    private final RentalService rentalService;

    @Autowired
    public CustomerFeedbackController(FeedbackService feedbackService, RentalService rentalService) {
        this.feedbackService = feedbackService;
        this.rentalService = rentalService;
    }

    @GetMapping
    public String listFeedbacks(Model model) {
        model.addAttribute("rentals", rentalService.getAllRentals());
        model.addAttribute("feedbacks", feedbackService.getAllFeedbacks());
        return "customer/feedback/feedbackList";
    }

    @GetMapping("/add")
    public String addFeedbackForm(Model model) {
        model.addAttribute("feedback", new FeedbackModel());
        model.addAttribute("rentals", rentalService.getAllRentals());
        return "customer/feedback/feedbackAdd";
    }

    @PostMapping("/add")
    public String addFeedback(@Valid @ModelAttribute("feedback") FeedbackModel feedback, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("rentals", rentalService.getAllRentals());
            return "customer/feedback/feedbackAdd";
        }
        feedback.setFeedbackDate(LocalDate.now());
        RentalModel rental = rentalService.getRentalById(feedback.getRental().getId());
        feedback.setRental(rental);
        feedbackService.saveFeedback(feedback);
        return "redirect:/customer/feedback";
    }

    @GetMapping("/edit/{id}")
    public String editFeedbackForm(@PathVariable Long id, Model model) {
        FeedbackModel feedbackModel = feedbackService.getFeedbackById(id);

        // Выводим данные отзыва в консоль
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getId().toString());
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getRental().getLocation().getName());
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getFeedbackDate().toString());
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getComment());
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getId().toString());
//        System.out.println("Полученные данные отзыва: " + feedbackModel.getId().toString());

        model.addAttribute("feedback", feedbackModel);
        model.addAttribute("rentals", rentalService.getAllRentals());
        return "customer/feedback/feedbackForm";
    }


    @PostMapping("/edit/{id}")
    public String editFeedback(@PathVariable Long id, @Valid @ModelAttribute FeedbackModel updatedFeedback, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("rentals", rentalService.getAllRentals());
            return "customer/feedback/feedbackForm";
        }
        updatedFeedback.setId(id);
        RentalModel rental = rentalService.getRentalById(updatedFeedback.getRental().getId());
        updatedFeedback.setRental(rental);

        feedbackService.updateFeedback(id, updatedFeedback);
        return "redirect:/customer/feedback";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return "redirect:/customer/feedback";
    }
}
