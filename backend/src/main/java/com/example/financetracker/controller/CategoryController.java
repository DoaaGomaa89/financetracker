package com.example.financetracker.controller;

import com.example.financetracker.dto.CategoryDto;
import com.example.financetracker.service.CategoryService;
import com.example.financetracker.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private JwtUtil jwtUtil;

    // GET /api/categories
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@RequestHeader("Authorization") String authHeader) {
        // Extract the JWT token from the Authorization header
        String token = extractToken(authHeader);

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).build();
        }

        List<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // GET /api/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Extract the JWT token from the Authorization header
        String token = extractToken(authHeader);

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).build();
        }

        CategoryDto category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }

    // POST /api/categories
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto, @RequestHeader("Authorization") String authHeader) {
        // Extract the JWT token from the Authorization header
        String token = extractToken(authHeader);

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).build();
        }

        CategoryDto createdCategory = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(201).body(createdCategory);
    }

    // DELETE /api/categories/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
        // Extract the JWT token from the Authorization header
        String token = extractToken(authHeader);

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(403).build();
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to extract the token from the "Authorization" header
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
