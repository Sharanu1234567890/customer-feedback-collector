package com.feedback.repository;

import com.feedback.model.FeedbackResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ResponseRepository extends JpaRepository<FeedbackResponse, Long> {
    List<FeedbackResponse> findByFormIdOrderBySubmittedAtDesc(Long formId);
    long countByFormId(Long formId);

    @org.springframework.data.jpa.repository.Query(
        "SELECT AVG(r.rating) FROM FeedbackResponse r WHERE r.form.id = :formId")
    Double findAverageRatingByFormId(Long formId);
}