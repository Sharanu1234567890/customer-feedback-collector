package com.feedback.controller;

import com.feedback.dto.DashboardResponse;
import com.feedback.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard/{formId}")
    public DashboardResponse getDashboard(@PathVariable Long formId) {
        return dashboardService.getDashboard(formId);
    }
}