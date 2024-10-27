package com.example.rentproject.repository;

import com.example.rentproject.model.MaintenanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<MaintenanceModel, Long> {
}
