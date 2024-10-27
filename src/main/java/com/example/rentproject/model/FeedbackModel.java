package com.example.rentproject.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;

@Entity
@Table(name = "feedback")
public class FeedbackModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Комментарий не может быть пустым")
    private String comment;

    @NotNull(message = "Оценка обязательна")
    @Min(value = 1, message = "Оценка должна быть не меньше 1")
    @Max(value = 5, message = "Оценка должна быть не больше 5")
    private Integer rating;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Дата отзыва обязательна")
    private LocalDate feedbackDate;

    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    @NotNull(message = "Необходимо указать поездку для оценки")
    private RentalModel rental;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Комментарий не может быть пустым") String getComment() {
        return comment;
    }

    public void setComment(@NotBlank(message = "Комментарий не может быть пустым") String comment) {
        this.comment = comment;
    }

    public @NotNull(message = "Оценка обязательна") @Min(value = 1, message = "Оценка должна быть не меньше 1") @Max(value = 5, message = "Оценка должна быть не больше 5") Integer getRating() {
        return rating;
    }

    public void setRating(@NotNull(message = "Оценка обязательна") @Min(value = 1, message = "Оценка должна быть не меньше 1") @Max(value = 5, message = "Оценка должна быть не больше 5") Integer rating) {
        this.rating = rating;
    }

    public @NotNull(message = "Дата отзыва обязательна") LocalDate getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(@NotNull(message = "Дата отзыва обязательна") LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public @NotNull(message = "Необходимо указать поездку для оценки") RentalModel getRental() {
        return rental;
    }

    public void setRental(@NotNull(message = "Необходимо указать поездку для оценки") RentalModel rental) {
        this.rental = rental;
    }
}
