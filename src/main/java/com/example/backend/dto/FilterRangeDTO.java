package com.example.backend.dto;

public record FilterRangeDTO(
        Integer monthSince,
        Integer yearSince,
        Integer monthUntil,
        Integer yearUntil
) {}
