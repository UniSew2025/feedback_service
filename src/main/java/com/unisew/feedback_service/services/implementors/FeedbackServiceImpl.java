package com.unisew.feedback_service.services.implementors;

import com.unisew.feedback_service.models.Feedback;
import com.unisew.feedback_service.models.FeedbackImage;
import com.unisew.feedback_service.repositories.FeedbackImageRepo;
import com.unisew.feedback_service.repositories.FeedbackRepo;
import com.unisew.feedback_service.requests.FeedBackRequest;
import com.unisew.feedback_service.responses.ResponseObject;
import com.unisew.feedback_service.services.FeedbackService;
import com.unisew.feedback_service.validations.FeedbackValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    FeedbackRepo feedbackRepo;

    FeedbackImageRepo feedbackImageRepo;

    @Override
    public ResponseEntity<ResponseObject> getFeedbackById(int feedbackId) {
        Feedback feedback = feedbackRepo.findById(feedbackId).orElse(null);
        if (feedback == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseObject.builder()
                            .message("Feedback not found")
                            .build());
        }

        Map<String, Object> data = buildFeedback(feedback);

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseObject.builder()
                        .message("Get feedback successfully")
                        .data(data)
                        .build());
    }



    private Map<String, Object> buildFeedback(Feedback feedback) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", feedback.getId());
        map.put("rating", feedback.getRating());
        map.put("content", feedback.getContent());
        map.put("isReport", feedback.isReport());
        map.put("creationDate", feedback.getCreationDate());
        map.put("images", buildFeedbackImages(feedback.getFeedbackImages()));
        return map;
    }

    private List<String> buildFeedbackImages(List<FeedbackImage> images) {
        if (images == null) return List.of();
        return images.stream()
                .map(FeedbackImage::getImageUrl)
                .toList();
    }

    @Override
    public ResponseEntity<ResponseObject> giveFeedbackOrReport(FeedBackRequest request) {
        String error = FeedbackValidation.validateFeedback(request);
        if (!error.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseObject.builder().message(error).build());
        }

        Feedback feedback = Feedback.builder()
                .content(request.getContent())
                .rating(request.getRating())
                .creationDate(LocalDate.now())
                .report(request.isReport())
                .build();

        Feedback savedFeedback = feedbackRepo.save(feedback);

        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            List<FeedbackImage> images = request.getImageUrl().stream()
                    .map(url -> FeedbackImage.builder()
                            .imageUrl(url)
                            .feedback(savedFeedback)
                            .build())
                    .toList();
            feedbackImageRepo.saveAll(images);
            savedFeedback.setFeedbackImages(images);
        }

        Map<String, Object> data = buildFeedback(savedFeedback);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseObject.builder()
                        .message("Feedback submitted successfully")
                        .data(data)
                        .build());
    }


}