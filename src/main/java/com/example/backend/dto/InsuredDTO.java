package com.example.backend.dto;

public record InsuredDTO(
        Integer ordre,
        String number,
        String name,
        Integer percentage,
        Double totalCASS,
        Double contributionCASS,
        Double totalIRPF,
        Double irpfWithHolding
) {
}
