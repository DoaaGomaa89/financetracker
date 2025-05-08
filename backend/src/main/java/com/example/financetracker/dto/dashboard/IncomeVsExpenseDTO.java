package com.example.financetracker.dto.dashboard;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncomeVsExpenseDTO {
    private BigDecimal income;
    private BigDecimal expenses;
}
