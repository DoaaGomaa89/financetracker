package com.example.financetracker.controller;

import com.example.financetracker.dto.TransactionDto;
import com.example.financetracker.model.Transaction;
import com.example.financetracker.service.TransactionService;
import com.example.financetracker.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private JwtUtil jwtUtil;

    // POST /api/transactions
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDto transactionDTO, HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        String email = jwtUtil.extractEmail(token);
        Transaction transaction = transactionService.createTransaction(transactionDTO, email);

        return ResponseEntity.ok(transaction);
    }
}
