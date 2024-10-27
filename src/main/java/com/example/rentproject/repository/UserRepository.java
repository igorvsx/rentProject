package com.example.rentproject.repository;

import com.example.rentproject.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
