package com.example.TransferProject.dto;

import com.example.TransferProject.tca.TransactionState;

import java.math.BigDecimal;

public record TransactionErrorResponse (
        String status,
        String errorCode,
        String errorMessage
){}