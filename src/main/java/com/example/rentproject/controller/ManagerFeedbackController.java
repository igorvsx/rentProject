package com.example.rentproject.controller;

import com.example.rentproject.model.FeedbackModel;
import com.example.rentproject.model.RentalModel;
import com.example.rentproject.repository.FeedbackRepository;
import com.example.rentproject.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/manager/feedback")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class ManagerFeedbackController {
    @Autowired
    private FeedbackRepository feedBackRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping()
    public String listFeedbacks(Model model) {
        List<RentalModel> rentals = rentalRepository.findAll();
        model.addAttribute("rentals", rentals);
        model.addAttribute("feedbacks", feedBackRepository.findAll());
        return "manager/feedback/feedbackList";
    }

    @GetMapping("/add")
    public String addFeedbackForm(Model model) {
        List<RentalModel> rentals = rentalRepository.findAll();
        model.addAttribute("rentals", rentals);
        model.addAttribute("feedback", new FeedbackModel());
        return "manager/feedback/feedbackAdd";
    }

    @PostMapping("/add")
    public String addFeedback(@Valid @ModelAttribute FeedbackModel feedback, BindingResult bindingResult, Model model) {
        List<RentalModel> rentals = rentalRepository.findAll();

        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("feedback", new FeedbackModel());
            model.addAttribute("rentals", rentals);
            return "manager/feedback/feedbackAdd";
        }

        feedBackRepository.save(feedback);
        return "redirect:/manager/feedback";
    }


    @GetMapping("/edit/{id}")
    public String editFeedbackForm(@PathVariable Long id, Model model) {
        FeedbackModel feedback = feedBackRepository.findById(id).orElseThrow();
        List<RentalModel> rentals = rentalRepository.findAll();
        model.addAttribute("rentals", rentals);
        model.addAttribute("feedback", feedback);
        return "manager/feedback/feedbackForm";
    }

    @PostMapping("/edit/{id}")
    public String editFeedback(@PathVariable Long id, @Valid @ModelAttribute FeedbackModel updatedFeedback, BindingResult result, Model model) {
        FeedbackModel existingFeedback = feedBackRepository.findById(id).orElseThrow();
        List<RentalModel> rentals = rentalRepository.findAll();
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("feedback", existingFeedback);
            model.addAttribute("rentals", rentals);
            return "manager/feedback/feedbackForm";
        }

        updatedFeedback.setId(existingFeedback.getId());
        feedBackRepository.save(updatedFeedback);
        return "redirect:/manager/feedback";
    }

    @GetMapping("/delete/{id}")
    public String deleteFeedback(@PathVariable Long id, Model model) {
        feedBackRepository.deleteById(id);
        return "redirect:/manager/feedback";
    }
}
