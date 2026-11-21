package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.actions.TransactionAction;

public class FinishedTransactionEffect implements Effect {

    private TransactionRecordRepository transactionRecordRepository;
    private TransactionState transactionState;

    public FinishedTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionState transactionState) {
        this.transactionRecordRepository=transactionRecordRepository;
        this.transactionState = transactionState;
    }

    @Override
    public TransactionAction run() {
        TransactionRecord t = new TransactionRecord(transactionState.transactionId(), transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
        transactionRecordRepository.save(t);
        return null;
    }
}
