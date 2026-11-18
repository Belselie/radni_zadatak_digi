package com.example.TransferProject.dto;

import com.example.TransferProject.tca.TransactionState;

import java.math.BigDecimal;

public record TransactionResponse (
        Long fromUserAccountId,
        Long toUserAccountId,
        BigDecimal amount_rsd,
        String status
){}
