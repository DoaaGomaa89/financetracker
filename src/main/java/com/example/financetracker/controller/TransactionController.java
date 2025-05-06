package com.example.financetracker.controller;

import com.example.financetracker.dto.TransactionDto;
import com.example.financetracker.model.Transaction;
import com.example.financetracker.model.User;
import com.example.financetracker.service.TransactionService;
import com.example.financetracker.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomUserDetailsService userService;

    @PostMapping("/create")
    public Transaction createTransaction(@RequestBody TransactionDto transactionDTO) {
        // Get the currently authenticated user
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Create and return the transaction
        return transactionService.createTransaction(transactionDTO, user);
    }
}

