package com.example.backend.dto;

public record ConceptDTO(
        String concept,
        Double amount,
        Integer hours,
        Integer days,
        Integer percentageCASS,
        Integer contribution,
        Integer percentageIRPF,
        Integer withHolding
) {
}
