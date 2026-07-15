package com.feedback.dto;

import java.util.List;

public class FormRequest {
    private String title;
    private List<QuestionRequest> questions;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<QuestionRequest> getQuestions() { return questions; }
    public void setQuestions(List<QuestionRequest> questions) { this.questions = questions; }

    public static class QuestionRequest {
        private String text;
        private String type;
        private Integer orderIndex;

        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public Integer getOrderIndex() { return orderIndex; }
        public void setOrderIndex(Integer orderIndex) { this.orderIndex = orderIndex; }
    }
}