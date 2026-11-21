package com.example.TransferProject.tca.effects;

import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.actions.CreateTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class DraftTransactionEffect implements Effect {

    private TransactionState transactionState;

    public DraftTransactionEffect(TransactionState transactionState) {
        this.transactionState = transactionState;
    }

    @Override
    public TransactionActionNew run() {
        System.out.println("Draft transaction state: \n" + transactionState.fromUserAccountId() + "\n" + transactionState.toUserAccountId() + "\n" + transactionState.amount_rsd() + "\n");
        return new CreateTransactionAction();
    }
}
