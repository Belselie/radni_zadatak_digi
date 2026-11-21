package com.example.TransferProject.tca.actions;

import com.example.TransferProject.tca.TransactionStatus;

public record FinishedTransactionAction(
        TransactionStatus status,
        String error
) implements TransactionAction {
}
