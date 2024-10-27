package com.example.rentproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "insurances")
public class InsuranceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Страховая компания не может быть пустой")
    private String insuranceProvider;

    @NotBlank(message = "Номер полиса не может быть пустым")
    private String policyNumber;

    @NotNull(message = "Сумма покрытия не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Сумма покрытия должна быть больше 0")
    private Double coverageAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата начала действия полиса не может быть пустой")
    private LocalDate validFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата окончания действия полиса не может быть пустой")
    @Future(message = "Дата окончания действия полиса должна быть в будущем")
    private LocalDate validUntil;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false, unique = true)
    @NotNull(message = "Необходимо указать автомобиль для страхования")
    private CarModel car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Страховая компания не может быть пустой") String getInsuranceProvider() {
        return insuranceProvider;
    }

    public void setInsuranceProvider(@NotBlank(message = "Страховая компания не может быть пустой") String insuranceProvider) {
        this.insuranceProvider = insuranceProvider;
    }

    public @NotBlank(message = "Номер полиса не может быть пустым") String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(@NotBlank(message = "Номер полиса не может быть пустым") String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public @NotNull(message = "Сумма покрытия не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Сумма покрытия должна быть больше 0") Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(@NotNull(message = "Сумма покрытия не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Сумма покрытия должна быть больше 0") Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    public @NotNull(message = "Дата начала действия полиса не может быть пустой") LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(@NotNull(message = "Дата начала действия полиса не может быть пустой") LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public @NotNull(message = "Дата окончания действия полиса не может быть пустой") @Future(message = "Дата окончания действия полиса должна быть в будущем") LocalDate getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(@NotNull(message = "Дата окончания действия полиса не может быть пустой") @Future(message = "Дата окончания действия полиса должна быть в будущем") LocalDate validUntil) {
        this.validUntil = validUntil;
    }

    public @NotNull(message = "Необходимо указать автомобиль для страхования") CarModel getCar() {
        return car;
    }

    public void setCar(@NotNull(message = "Необходимо указать автомобиль для страхования") CarModel car) {
        this.car = car;
    }
}
