package com.example.TransferProject.tca.actions;

import java.math.BigDecimal;

public record DraftTransactionAction(
        Long fromAccountId,
        Long toAccountId,
        BigDecimal amount_rsd
) implements TransactionActionNew {

}
