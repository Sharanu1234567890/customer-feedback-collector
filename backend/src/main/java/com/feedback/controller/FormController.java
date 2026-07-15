package com.feedback.controller;

import com.feedback.dto.FormRequest;
import com.feedback.model.Form;
import com.feedback.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FormController {

    @Autowired
    private FormService formService;

    @PostMapping("/forms")
    public Form create(@RequestBody FormRequest request) {
        return formService.createForm(request);
    }

    @GetMapping("/forms")
    public List<Form> getAll() {
        return formService.getAll();
    }

    @GetMapping("/form/{token}")
    public Form getByToken(@PathVariable String token) {
        return formService.getByToken(token);
    }

    @PutMapping("/forms/{id}")
    public Form update(@PathVariable Long id, @RequestBody FormRequest request) {
        return formService.updateForm(id, request);
    }

    @DeleteMapping("/forms/{id}")
    public void delete(@PathVariable Long id) {
        formService.deleteForm(id);
    }
}