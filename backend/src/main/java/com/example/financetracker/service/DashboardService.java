package com.example.financetracker.service;

import com.example.financetracker.dto.dashboard.CategoryBreakdownDTO;
import com.example.financetracker.dto.dashboard.DashboardDTO;
import com.example.financetracker.dto.dashboard.IncomeVsExpenseDTO;
import com.example.financetracker.dto.dashboard.MonthlyTrendDTO;
import com.example.financetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DashboardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public DashboardDTO getDashboardData(Long userId) {
        // Income vs Expense
        BigDecimal totalIncome = transactionRepository.getTotalIncome(userId);
        BigDecimal totalExpenses = transactionRepository.getTotalExpenses(userId);

        // Category breakdown
        List<Object[]> categoryData = transactionRepository.getExpensesByCategory(userId);
        List<String> categoryLabels = new ArrayList<>();
        List<BigDecimal> categoryValues = new ArrayList<>();
        for (Object[] row : categoryData) {
            categoryLabels.add((String) row[0]);
            categoryValues.add((BigDecimal) row[1]);
        }

        // Monthly trend
        List<Object[]> monthlyData = transactionRepository.getMonthlyIncomeExpense(userId);
        Map<String, BigDecimal> incomeMap = new LinkedHashMap<>();
        Map<String, BigDecimal> expenseMap = new LinkedHashMap<>();

        for (Object[] row : monthlyData) {
            String month = (String) row[0];
            String type = row[1].toString();
            BigDecimal amount = (BigDecimal) row[2];
            if (type.equals("INCOME")) {
                incomeMap.put(month, amount);
            } else {
                expenseMap.put(month, amount);
            }
        }

        // Align months
        Set<String> allMonths = new TreeSet<>(incomeMap.keySet());
        allMonths.addAll(expenseMap.keySet());

        List<String> labels = new ArrayList<>(allMonths);
        List<BigDecimal> incomeValues = labels.stream().map(m -> incomeMap.getOrDefault(m, BigDecimal.ZERO)).toList();
        List<BigDecimal> expenseValues = labels.stream().map(m -> expenseMap.getOrDefault(m, BigDecimal.ZERO)).toList();

        return DashboardDTO.builder()
                .incomeVsExpenses(new IncomeVsExpenseDTO(totalIncome, totalExpenses))
                .expensesByCategory(new CategoryBreakdownDTO(categoryLabels, categoryValues))
                .monthlyTrend(new MonthlyTrendDTO(labels, incomeValues, expenseValues))
                .build();
    }
}

