package com.example.financetracker.dto.dashboard;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class DashboardDTO {
    private IncomeVsExpenseDTO incomeVsExpenses;
    private CategoryBreakdownDTO expensesByCategory;
    private MonthlyTrendDTO monthlyTrend;
}

