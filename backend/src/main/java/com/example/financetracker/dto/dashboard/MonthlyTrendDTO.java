package com.example.financetracker.dto.dashboard;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyTrendDTO {
    private List<String> labels; // e.g. ["Jan", "Feb"]
    private List<BigDecimal> income;
    private List<BigDecimal> expenses;
}
