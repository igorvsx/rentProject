package com.example.rentproject.controller;

import com.example.rentproject.model.PaymentModel;
import com.example.rentproject.repository.PaymentRepository;
import com.example.rentproject.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/manager/payment")
@PreAuthorize("hasAnyAuthority('MANAGER')")
public class PaymentController {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @GetMapping()
    public String listPayments(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        model.addAttribute("payments", paymentRepository.findAll());
        return "manager/payment/paymentList";
    }

    @GetMapping("/add")
    public String addPaymentForm(Model model) {
        model.addAttribute("rentals", rentalRepository.findAll());
        model.addAttribute("payment", new PaymentModel());
        return "manager/payment/paymentAdd";
    }

    @PostMapping("/add")
    public String addPayment(@Valid @ModelAttribute PaymentModel payment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("message", bindingResult.getAllErrors());
            model.addAttribute("payment", new PaymentModel());
            model.addAttribute("rentals", rentalRepository.findAll());
            return "manager/payment/paymentAdd";
        }

        paymentRepository.save(payment);
        return "redirect:/manager/payment";
    }


    @GetMapping("/edit/{id}")
    public String editPaymentForm(@PathVariable Long id, Model model) {
        PaymentModel payment = paymentRepository.findById(id).orElseThrow();
        model.addAttribute("rentals", rentalRepository.findAll());
        model.addAttribute("payment", payment);
        return "manager/payment/paymentForm";
    }

    @PostMapping("/edit/{id}")
    public String editPayment(@PathVariable Long id, @Valid @ModelAttribute PaymentModel updatedPayment, BindingResult result, Model model) {
        PaymentModel existingPayment = paymentRepository.findById(id).orElseThrow();
        if (result.hasErrors()) {
            model.addAttribute("message", result.getAllErrors());
            model.addAttribute("payment", existingPayment);
            model.addAttribute("rentals", rentalRepository.findAll());
            return "manager/payment/paymentForm";
        }

        updatedPayment.setId(existingPayment.getId());
        paymentRepository.save(updatedPayment);
        return "redirect:/manager/payment";
    }

    @GetMapping("/delete/{id}")
    public String deletePayment(@PathVariable Long id, Model model) {
        paymentRepository.deleteById(id);
        return "redirect:/manager/payment";
    }
}
