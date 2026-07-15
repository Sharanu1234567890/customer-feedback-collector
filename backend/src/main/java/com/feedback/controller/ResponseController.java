package com.feedback.controller;

import com.feedback.dto.ResponseRequest;
import com.feedback.model.FeedbackResponse;
import com.feedback.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ResponseController {

    @Autowired
    private ResponseService responseService;

    @PostMapping("/responses")
    public FeedbackResponse submit(@RequestBody ResponseRequest request) {
        return responseService.submit(request);
    }
}