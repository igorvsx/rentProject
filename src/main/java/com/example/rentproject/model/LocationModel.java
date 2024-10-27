package com.example.rentproject.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;

@Entity
@Table(name = "locations")
public class LocationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название локации не может быть пустым")
    private String name;

    @NotBlank(message = "Адрес не может быть пустым")
    private String address;

    @OneToMany(mappedBy = "location")
    private Set<RentalModel> rentals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Название локации не может быть пустым") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "Название локации не может быть пустым") String name) {
        this.name = name;
    }

    public @NotBlank(message = "Адрес не может быть пустым") String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank(message = "Адрес не может быть пустым") String address) {
        this.address = address;
    }

    public Set<RentalModel> getRentals() {
        return rentals;
    }

    public void setRentals(Set<RentalModel> rentals) {
        this.rentals = rentals;
    }
}
