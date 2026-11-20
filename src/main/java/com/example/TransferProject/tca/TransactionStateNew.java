package com.example.TransferProject.tca;

import java.math.BigDecimal;

public record TransactionStateNew(
        Long fromUserAccountId,
        Long toUserAccountId,
        BigDecimal amount_rsd,
        TransactionStatusNew status,
        String error
) {

    static TransactionStateNew init(){
        return new TransactionStateNew(null,null,null,null,null);

    }
}