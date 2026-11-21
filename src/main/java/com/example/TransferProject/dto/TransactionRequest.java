package com.example.TransferProject.dto;

import java.math.BigDecimal;

public record TransactionRequest (

    Long fromUserAccountId,
    Long toUserAccountId,
    BigDecimal amount_rsd
){}
