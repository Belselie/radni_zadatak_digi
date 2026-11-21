package com.example.TransferProject.tca;

import java.math.BigDecimal;

public record TransactionState(
        Long fromUserAccountId,
        Long toUserAccountId,
        BigDecimal amount_rsd,
        TransactionStatus status,
        String error
) {

    static TransactionState init(){
        return new TransactionState(null,null,null,null,null);

    }
}