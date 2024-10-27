package com.example.rentproject.controller;

import com.example.rentproject.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
@PreAuthorize("hasAnyAuthority('CUSTOMER')")
public class FeedBackController {
    @Autowired
    private FeedbackRepository feedBackRepository;

    @GetMapping()
    public String listFeedbacks(Model model) {
        model.addAttribute("feedbacks", feedBackRepository.findAll());
        return "customer/feedbackList";
    }

}
