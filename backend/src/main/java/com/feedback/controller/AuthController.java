package com.feedback.controller;

import com.feedback.dto.LoginRequest;
import com.feedback.repository.AdminRepository;
import com.feedback.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        return adminRepository.findByEmail(request.getEmail())
                .filter(a -> a.getPassword().equals(request.getPassword()))
                .map(a -> {
                    Map<String, String> res = new HashMap<>();
                    res.put("token", "demo-token-" + a.getId());
                    return ResponseEntity.ok(res);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}