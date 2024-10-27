package com.example.rentproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Сумма платежа не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма платежа должна быть положительной")
    private Double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата платежа не может быть пустой")
    private LocalDate paymentDate;

    @NotBlank(message = "Способ оплаты не может быть пустым")
    private String paymentMethod;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    @NotNull(message = "Платеж должен быть связан с арендой")
    private RentalModel rental;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Сумма платежа не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Сумма платежа должна быть положительной") Double getAmount() {
        return amount;
    }

    public void setAmount(@NotNull(message = "Сумма платежа не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Сумма платежа должна быть положительной") Double amount) {
        this.amount = amount;
    }

    public @NotNull(message = "Дата платежа не может быть пустой") LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(@NotNull(message = "Дата платежа не может быть пустой") LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public @NotBlank(message = "Способ оплаты не может быть пустым") String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(@NotBlank(message = "Способ оплаты не может быть пустым") String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public @NotNull(message = "Платеж должен быть связан с арендой") RentalModel getRental() {
        return rental;
    }

    public void setRental(@NotNull(message = "Платеж должен быть связан с арендой") RentalModel rental) {
        this.rental = rental;
    }
}
