//package com.example.rentproject.repository;
//
//import com.example.rentproject.model.FeedbackModel;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//
//public interface FeedbackRepository extends JpaRepository<FeedbackModel, Long> {
//    @Query("SELECT f FROM FeedbackModel f WHERE f.rental.user.userId = :userId")
//    List<FeedbackModel> findByRentalUserId(Long userId);
//
//    boolean existsByRentalId(Long rentalId);
//}
