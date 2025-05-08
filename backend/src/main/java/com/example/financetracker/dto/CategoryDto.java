package com.example.financetracker.dto;

import com.example.financetracker.enums.TransactionType;
import com.example.financetracker.model.Transaction;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class CategoryDto {
    private Long id;
    private String name;
    private TransactionType type; // INCOME or EXPENSE
    private Set<TransactionDto> transactions;
}