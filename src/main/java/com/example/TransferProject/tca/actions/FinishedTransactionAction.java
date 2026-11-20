package com.example.TransferProject.tca.actions;

import com.example.TransferProject.tca.TransactionStatusNew;

public record FinishedTransactionAction(
        TransactionStatusNew status,
        String error
) implements TransactionActionNew {
}
