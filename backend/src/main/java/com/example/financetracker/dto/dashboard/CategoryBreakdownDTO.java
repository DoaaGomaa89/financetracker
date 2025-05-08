package com.example.financetracker.dto.dashboard;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryBreakdownDTO {
    private List<String> labels;
    private List<BigDecimal> values;
}
