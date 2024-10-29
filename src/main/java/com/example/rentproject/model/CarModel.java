package com.example.rentproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Марка автомобиля не может быть пустой")
    private String make;

    @NotBlank(message = "Модель автомобиля не может быть пустой")
    private String model;

    @Min(value = 1886, message = "Год выпуска должен быть не ранее 1886 года")
    @Max(value = 2100, message = "Год выпуска должен быть реальным")
    private int year;

    @NotBlank(message = "Номерной знак не может быть пустым")
    @Pattern(regexp = "^[A-Z0-9-]+$", message = "Номерной знак может содержать только буквы, цифры и дефисы")
    private String licensePlate;

    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private InsuranceModel insurances;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Марка автомобиля не может быть пустой") String getMake() {
        return make;
    }

    public void setMake(@NotBlank(message = "Марка автомобиля не может быть пустой") String make) {
        this.make = make;
    }

    public @NotBlank(message = "Модель автомобиля не может быть пустой") String getModel() {
        return model;
    }

    public void setModel(@NotBlank(message = "Модель автомобиля не может быть пустой") String model) {
        this.model = model;
    }

    @Min(value = 1886, message = "Год выпуска должен быть не ранее 1886 года")
    @Max(value = 2100, message = "Год выпуска должен быть реальным")
    public int getYear() {
        return year;
    }

    public void setYear(@Min(value = 1886, message = "Год выпуска должен быть не ранее 1886 года") @Max(value = 2100, message = "Год выпуска должен быть реальным") int year) {
        this.year = year;
    }

    public @NotBlank(message = "Номерной знак не может быть пустым") @Pattern(regexp = "^[A-Z0-9-]+$", message = "Номерной знак может содержать только буквы, цифры и дефисы") String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(@NotBlank(message = "Номерной знак не может быть пустым") @Pattern(regexp = "^[A-Z0-9-]+$", message = "Номерной знак может содержать только буквы, цифры и дефисы") String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public InsuranceModel getInsurances() {
        return insurances;
    }

    public void setInsurances(InsuranceModel insurances) {
        this.insurances = insurances;
    }
}
