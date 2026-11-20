package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class FinishedTransactionEffect implements Effect {

    private TransactionRecordRepository transactionRecordRepository;
    private TransactionStateNew transactionStateNew;

    public FinishedTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionStateNew transactionStateNew) {
        this.transactionRecordRepository=transactionRecordRepository;
        this.transactionStateNew=transactionStateNew;
    }

    @Override
    public TransactionActionNew run() {
        System.out.println("Finished writing. State is: " +transactionStateNew.status());
        TransactionRecord t = new TransactionRecord(transactionStateNew.fromUserAccountId(), transactionStateNew.toUserAccountId(), transactionStateNew.amount_rsd(), transactionStateNew.status(), transactionStateNew.error());
        transactionRecordRepository.save(t);
        System.out.println("Finished done writing");
        return null;
    }
}
