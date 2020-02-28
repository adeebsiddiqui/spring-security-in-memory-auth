package com.ad.app.controller;

import com.ad.app.model.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/spring-security-memory-auth")
public class SpringSecAppController {

    @Autowired
    private Grade grade;

    @GetMapping("/greetings")
    public ResponseEntity<String> greet() {
        return ResponseEntity.ok("Hello Spring Security In-Memory Authentication!!");
    }

    @GetMapping("/grades")
    public ResponseEntity<Grade> greetClass() {
        return ResponseEntity.ok(grade);
    }

    @PostMapping("/grades")
    public ResponseEntity<Grade> greetTeacher(@RequestBody Grade grade) {
        this.grade = grade;
        return ResponseEntity.status(HttpStatus.CREATED).body(grade);
    }
}
