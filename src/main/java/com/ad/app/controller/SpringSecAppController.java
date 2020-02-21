package com.ad.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spring-security-memory-auth")
public class SpringSecAppController {

    @GetMapping("/greetings")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello Spring Security In-Memory Authentication!!");
    }
}
