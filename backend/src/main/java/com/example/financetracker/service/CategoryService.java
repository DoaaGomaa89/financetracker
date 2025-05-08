package com.example.financetracker.service;

import com.example.financetracker.dto.CategoryDto;
import com.example.financetracker.dto.TransactionDto;
import com.example.financetracker.model.Category;
import com.example.financetracker.model.Transaction;
import com.example.financetracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // Create a new category
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = Category.builder()
                .name(categoryDto.getName())
                .type(categoryDto.getType())
                .build();

        category = categoryRepository.save(category); // Save to DB

        return mapToDto(category); // Convert entity to DTO for response
    }

    // Get all categories
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories.stream()
                .map(this::mapToDto) // Convert entities to DTOs
                .collect(Collectors.toList());
    }

    // Get category by ID
    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return mapToDto(category);
    }

    // Delete category by ID
    public void deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    // Map Category entity to CategoryDto, including simplified transactions
    private CategoryDto mapToDto(Category category) {
        Set<TransactionDto> transactionDtos = category.getTransactions().stream()
                .map(this::mapTransactionToDto)
                .collect(Collectors.toSet());

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .type(category.getType())
                .transactions(transactionDtos) // Include simplified transactions
                .build();
    }

    // Map Transaction entity to TransactionDto
    private TransactionDto mapTransactionToDto(Transaction transaction) {
        return TransactionDto.builder()
                .id(transaction.getId())
                .title(transaction.getTitle())
                .amount(transaction.getAmount())
                .type(transaction.getType())
                .userId(transaction.getUser().getId())
                .categoryId(transaction.getCategory().getId())
                .date(transaction.getDate())
                .notes(transaction.getNotes())
                .build();
    }
}
