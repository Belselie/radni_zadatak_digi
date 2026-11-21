package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.actions.PendingTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class CreateTransactionEffect implements Effect {
    private final TransactionRecordRepository transactionRecordRepository;
    private final TransactionState transactionState;

    public CreateTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionState transactionState) {
        this.transactionRecordRepository = transactionRecordRepository;
        this.transactionState = transactionState;
    }

    @Override
    public TransactionActionNew run() {

        System.out.println("Started Create");
        TransactionRecord t = new TransactionRecord(transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
        transactionRecordRepository.save(t);
        System.out.println("Finished Create");
        return new PendingTransactionAction();
    }
}
