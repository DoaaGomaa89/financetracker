package com.example.financetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.financetracker.model.Transaction;
import com.example.financetracker.model.User;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'INCOME'")
    BigDecimal getTotalIncome(Long userId);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.user.id = :userId AND t.type = 'EXPENSE'")
    BigDecimal getTotalExpenses(Long userId);

    @Query("SELECT t.category.name, SUM(t.amount) " +
            "FROM Transaction t WHERE t.user.id = :userId AND t.type = 'EXPENSE' " +
            "GROUP BY t.category.name")
    List<Object[]> getExpensesByCategory(Long userId);

    @Query("SELECT FUNCTION('TO_CHAR', t.date, 'YYYY-MM'), t.type, SUM(t.amount) " +
            "FROM Transaction t WHERE t.user.id = :userId " +
            "GROUP BY FUNCTION('TO_CHAR', t.date, 'YYYY-MM'), t.type " +
            "ORDER BY FUNCTION('TO_CHAR', t.date, 'YYYY-MM')")
    List<Object[]> getMonthlyIncomeExpense(Long userId);
}
