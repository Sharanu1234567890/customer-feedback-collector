package com.feedback.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "answers")
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "response_id")
    @JsonIgnore
    private FeedbackResponse response;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String value;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public FeedbackResponse getResponse() { return response; }
    public void setResponse(FeedbackResponse response) { this.response = response; }
    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }
    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
}