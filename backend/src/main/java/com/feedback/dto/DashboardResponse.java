package com.feedback.dto;

import java.util.List;

public class DashboardResponse {
    private long totalResponses;
    private double averageRating;
    private List<RecentResponse> recent;

    public long getTotalResponses() { return totalResponses; }
    public void setTotalResponses(long totalResponses) { this.totalResponses = totalResponses; }
    public double getAverageRating() { return averageRating; }
    public void setAverageRating(double averageRating) { this.averageRating = averageRating; }
    public List<RecentResponse> getRecent() { return recent; }
    public void setRecent(List<RecentResponse> recent) { this.recent = recent; }

    public static class RecentResponse {
        private Long id;
        private Integer rating;
        private String submittedAt;

        public RecentResponse(Long id, Integer rating, String submittedAt) {
            this.id = id;
            this.rating = rating;
            this.submittedAt = submittedAt;
        }
        public Long getId() { return id; }
        public Integer getRating() { return rating; }
        public String getSubmittedAt() { return submittedAt; }
    }
}