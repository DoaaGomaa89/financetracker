package com.example.financetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.financetracker.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto {
    private Long id;
    private String title;
    private BigDecimal amount;
    private TransactionType type;
    private Long userId;
    private Long categoryId;
    private LocalDateTime date;
    private String notes;
}

