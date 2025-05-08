package com.example.financetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.financetracker.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
