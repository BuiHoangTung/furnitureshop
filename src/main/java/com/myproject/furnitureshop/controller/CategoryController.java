package com.myproject.furnitureshop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @PostMapping
    public ResponseEntity<?> createCategory() {

        return ResponseEntity.ok().body("oke.");
    }

    @GetMapping
    public ResponseEntity<?> getCategories() {

        return ResponseEntity.ok(List.of("Hello", "oke", "ahehe"));
    }
}
