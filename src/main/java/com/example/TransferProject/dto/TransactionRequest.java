package com.example.TransferProject.dto;

import java.math.BigDecimal;

//@ValidateEqualValues(fromUser,toUser)
public record TransactionRequest (

    Long fromUserAccountId,
    Long toUserAccountId,
    BigDecimal amount_rsd
){}
