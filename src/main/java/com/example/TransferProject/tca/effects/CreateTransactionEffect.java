package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import com.example.TransferProject.tca.actions.FinishedTransactionAction;
import com.example.TransferProject.tca.actions.PendingTransactionAction;
import com.example.TransferProject.tca.actions.TransactionAction;

public class CreateTransactionEffect implements Effect {
    private final TransactionRecordRepository transactionRecordRepository;
    private final TransactionState transactionState;

    public CreateTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionState transactionState) {
        this.transactionRecordRepository = transactionRecordRepository;
        this.transactionState = transactionState;
    }

    @Override
    public TransactionAction run() {


        TransactionRecord transactionId = transactionRecordRepository.findById(transactionState.transactionId()).orElse(null);

        if (transactionId != null){
            return new FinishedTransactionAction(TransactionStatus.FAILED, "transaction already exists");
        }

        else {
            TransactionRecord t = new TransactionRecord(transactionState.transactionId(), transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
            transactionRecordRepository.save(t);

            return new PendingTransactionAction();
        }
    }
}
