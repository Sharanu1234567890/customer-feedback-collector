package com.feedback.service;

import com.feedback.dto.DashboardResponse;
import com.feedback.model.FeedbackResponse;
import com.feedback.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private ResponseRepository responseRepository;

    public DashboardResponse getDashboard(Long formId) {
        long total = responseRepository.countByFormId(formId);
        Double avg = responseRepository.findAverageRatingByFormId(formId);

        List<FeedbackResponse> recentResponses = responseRepository
                .findByFormIdOrderBySubmittedAtDesc(formId)
                .stream().limit(5).collect(Collectors.toList());

        List<DashboardResponse.RecentResponse> recent = recentResponses.stream()
                .map(r -> new DashboardResponse.RecentResponse(
                        r.getId(), r.getRating(), r.getSubmittedAt().toString()))
                .collect(Collectors.toList());

        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setTotalResponses(total);
        dashboard.setAverageRating(avg != null ? avg : 0.0);
        dashboard.setRecent(recent);
        return dashboard;
    }
}