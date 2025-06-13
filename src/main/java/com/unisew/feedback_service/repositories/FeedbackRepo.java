package com.unisew.feedback_service.repositories;

import com.unisew.feedback_service.models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
}
