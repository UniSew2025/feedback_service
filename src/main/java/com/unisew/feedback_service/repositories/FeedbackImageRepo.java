package com.unisew.feedback_service.repositories;

import com.unisew.feedback_service.models.FeedbackImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackImageRepo extends JpaRepository<FeedbackImage, Integer> {
}
