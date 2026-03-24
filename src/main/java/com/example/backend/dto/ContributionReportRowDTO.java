package com.example.backend.dto;


import java.time.LocalDate;
import java.time.YearMonth;

public record ContributionReportRowDTO(
        YearMonth period,
        String sheetNumber,
        String irpfNumber,
        LocalDate submissionDate,
        Double total,
        Double payment,
        String status
) {
}
