package com.feedback.service;

import com.feedback.model.Question;
import com.feedback.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getByFormId(Long formId) {
        return questionRepository.findByFormIdOrderByOrderIndexAsc(formId);
    }
}