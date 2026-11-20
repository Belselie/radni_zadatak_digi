package com.example.TransferProject.tca.effects;

import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.actions.CreateTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class DraftTransactionEffect implements Effect {

    private TransactionStateNew transactionStateNew;

    public DraftTransactionEffect(TransactionStateNew transactionStateNew) {
        this.transactionStateNew = transactionStateNew;
    }

    @Override
    public TransactionActionNew run() {
        System.out.println("Draft transaction state: \n" + transactionStateNew.fromUserAccountId() + "\n" + transactionStateNew.toUserAccountId() + "\n" +transactionStateNew.amount_rsd() + "\n");
        return new CreateTransactionAction();
    }
}
