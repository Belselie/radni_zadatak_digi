package com.example.TransferProject.tca;


import java.math.BigDecimal;

public record TransactionState(
        Long fromUserAccountId,
        Long toUserAccountId,
        BigDecimal amount_rsd,
        Status status,
        String error

){
    public enum Status {READY, IN_PROGRESS, SUCCESS, FAILED}

    public static TransactionState ready() {
        return new TransactionState(null,null,null,Status.READY, null);
    }
}

