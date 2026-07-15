package com.feedback.service;

import com.feedback.dto.ResponseRequest;
import com.feedback.model.Answer;
import com.feedback.model.FeedbackResponse;
import com.feedback.model.Form;
import com.feedback.model.Question;
import com.feedback.repository.QuestionRepository;
import com.feedback.repository.ResponseRepository;
import com.feedback.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResponseService {

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private FormService formService;

    @Autowired
    private QuestionRepository questionRepository;

    public FeedbackResponse submit(ResponseRequest request) {
        Form form = formService.getByToken(request.getToken());

        FeedbackResponse response = new FeedbackResponse();
        response.setForm(form);
        response.setRating(request.getRating());

        List<Answer> answers = new ArrayList<>();
        if (request.getAnswers() != null) {
            for (ResponseRequest.AnswerRequest ar : request.getAnswers()) {
                Question question = questionRepository.findById(ar.getQuestionId())
                        .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
                Answer answer = new Answer();
                answer.setQuestion(question);
                answer.setValue(ar.getValue());
                answer.setResponse(response);
                answers.add(answer);
            }
        }
        response.setAnswers(answers);
        return responseRepository.save(response);
    }
}