package com.example.rentproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "maintenance")
public class MaintenanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Тип обслуживания не может быть пустым")
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата обслуживания не может быть пустой")
    @PastOrPresent(message = "Дата обслуживания не может быть в будущем")
    private LocalDate date;

    @NotNull(message = "Стоимость не может быть пустой")
    @DecimalMin(value = "0.0", inclusive = false, message = "Стоимость должна быть положительным числом")
    private Double cost;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @NotNull(message = "Необходимо указать автомобиль для обслуживания")
    private CarModel car;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Тип обслуживания не может быть пустым") String getType() {
        return type;
    }

    public void setType(@NotBlank(message = "Тип обслуживания не может быть пустым") String type) {
        this.type = type;
    }

    public @NotNull(message = "Дата обслуживания не может быть пустой") @PastOrPresent(message = "Дата обслуживания не может быть в будущем") LocalDate getDate() {
        return date;
    }

    public void setDate(@NotNull(message = "Дата обслуживания не может быть пустой") @PastOrPresent(message = "Дата обслуживания не может быть в будущем") LocalDate date) {
        this.date = date;
    }

    public @NotNull(message = "Стоимость не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Стоимость должна быть положительным числом") Double getCost() {
        return cost;
    }

    public void setCost(@NotNull(message = "Стоимость не может быть пустой") @DecimalMin(value = "0.0", inclusive = false, message = "Стоимость должна быть положительным числом") Double cost) {
        this.cost = cost;
    }

    public @NotNull(message = "Необходимо указать автомобиль для обслуживания") CarModel getCar() {
        return car;
    }

    public void setCar(@NotNull(message = "Необходимо указать автомобиль для обслуживания") CarModel car) {
        this.car = car;
    }
}
