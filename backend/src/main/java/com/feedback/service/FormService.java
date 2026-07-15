package com.feedback.service;

import com.feedback.dto.FormRequest;
import com.feedback.model.Form;
import com.feedback.model.Question;
import com.feedback.repository.FormRepository;
import com.feedback.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FormService {

    @Autowired
    private FormRepository formRepository;

    public Form createForm(FormRequest request) {
        Form form = new Form();
        form.setTitle(request.getTitle());
        form.setToken(UUID.randomUUID().toString().substring(0, 8));

        List<Question> questions = new ArrayList<>();
        if (request.getQuestions() != null) {
            for (FormRequest.QuestionRequest qr : request.getQuestions()) {
                Question q = new Question();
                q.setText(qr.getText());
                q.setType(qr.getType());
                q.setOrderIndex(qr.getOrderIndex());
                q.setForm(form);
                questions.add(q);
            }
        }
        form.setQuestions(questions);
        return formRepository.save(form);
    }

    public Form getByToken(String token) {
        return formRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));
    }

    public Form getById(Long id) {
        return formRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));
    }

    public List<Form> getAll() {
        return formRepository.findAll();
    }

    public Form updateForm(Long id, FormRequest request) {
        Form form = getById(id);
        form.setTitle(request.getTitle());

        form.getQuestions().clear();
        if (request.getQuestions() != null) {
            for (FormRequest.QuestionRequest qr : request.getQuestions()) {
                Question q = new Question();
                q.setText(qr.getText());
                q.setType(qr.getType());
                q.setOrderIndex(qr.getOrderIndex());
                q.setForm(form);
                form.getQuestions().add(q);
            }
        }
        return formRepository.save(form);
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }
}