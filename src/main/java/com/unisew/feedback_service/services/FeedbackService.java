package com.unisew.feedback_service.services;

import com.unisew.feedback_service.requests.FeedBackRequest;
import com.unisew.feedback_service.responses.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<ResponseObject> getFeedbackById(int feedbackId);

    ResponseEntity<ResponseObject> giveFeedbackOrReport(FeedBackRequest request);
}