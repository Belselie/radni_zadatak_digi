package com.example.TransferProject.tca.effects;

import com.example.TransferProject.tca.actions.CreateTransactionAction;
import com.example.TransferProject.tca.actions.TransactionAction;

public class DraftTransactionEffect implements Effect {

    public DraftTransactionEffect() {}

    @Override
    public TransactionAction run() {
        return new CreateTransactionAction();
    }
}
