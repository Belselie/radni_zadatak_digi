package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.actions.PendingTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class CreateTransactionEffect implements Effect {
    private final TransactionRecordRepository transactionRecordRepository;
    private final TransactionStateNew transactionStateNew;

    public CreateTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionStateNew transactionStateNew) {
        this.transactionRecordRepository = transactionRecordRepository;
        this.transactionStateNew = transactionStateNew;
    }

    @Override
    public TransactionActionNew run() {

        System.out.println("Started Create");
        TransactionRecord t = new TransactionRecord(transactionStateNew.fromUserAccountId(), transactionStateNew.toUserAccountId(), transactionStateNew.amount_rsd(), transactionStateNew.status(),transactionStateNew.error());
        transactionRecordRepository.save(t);
        System.out.println("Finished Create");
        return new PendingTransactionAction();
    }
}
