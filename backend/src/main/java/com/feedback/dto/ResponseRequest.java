package com.feedback.dto;

import java.util.List;

public class ResponseRequest {
    private String token;
    private Integer rating;
    private List<AnswerRequest> answers;

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }
    public List<AnswerRequest> getAnswers() { return answers; }
    public void setAnswers(List<AnswerRequest> answers) { this.answers = answers; }

    public static class AnswerRequest {
        private Long questionId;
        private String value;

        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}