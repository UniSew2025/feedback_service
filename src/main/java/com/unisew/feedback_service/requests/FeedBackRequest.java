package com.unisew.feedback_service.requests;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FeedBackRequest {
    String content;
    Integer rating;
    Integer designRequestId;
    Integer schoolId;
    List<String> imageUrl;
    boolean isReport;
}
