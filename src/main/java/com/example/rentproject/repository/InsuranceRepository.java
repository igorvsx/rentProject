package com.example.rentproject.repository;

import com.example.rentproject.model.CarModel;
import com.example.rentproject.model.InsuranceModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<InsuranceModel, Long> {
    Optional<InsuranceModel> findByCar(CarModel car);
}
