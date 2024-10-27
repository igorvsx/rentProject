package com.example.rentproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class RentalModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата начала аренды не может быть пустой")
    @FutureOrPresent(message = "Дата начала аренды не может быть в прошлом")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Дата окончания аренды должна быть в будущем")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @NotNull(message = "Необходимо указать автомобиль для аренды")
    private CarModel car;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Необходимо указать пользователя для аренды")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false) // добавляем связь с LocationModel
    @NotNull(message = "Необходимо указать локацию для аренды")
    private LocationModel location;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "Дата начала аренды не может быть пустой") @FutureOrPresent(message = "Дата начала аренды не может быть в прошлом") LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull(message = "Дата начала аренды не может быть пустой") @FutureOrPresent(message = "Дата начала аренды не может быть в прошлом") LocalDate startDate) {
        this.startDate = startDate;
    }

    public @NotNull(message = "Дата окончания аренды не может быть пустой") @Future(message = "Дата окончания аренды должна быть в будущем") LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(@NotNull(message = "Дата окончания аренды не может быть пустой") @Future(message = "Дата окончания аренды должна быть в будущем") LocalDate endDate) {
        this.endDate = endDate;
    }

    public @NotNull(message = "Необходимо указать автомобиль для аренды") CarModel getCar() {
        return car;
    }

    public void setCar(@NotNull(message = "Необходимо указать автомобиль для аренды") CarModel car) {
        this.car = car;
    }

    public @NotNull(message = "Необходимо указать пользователя для аренды") UserModel getUser() {
        return user;
    }

    public void setUser(@NotNull(message = "Необходимо указать пользователя для аренды") UserModel user) {
        this.user = user;
    }

    public @NotNull(message = "Необходимо указать локацию для аренды") LocationModel getLocation() {
        return location;
    }

    public void setLocation(@NotNull(message = "Необходимо указать локацию для аренды") LocationModel location) {
        this.location = location;
    }
}
