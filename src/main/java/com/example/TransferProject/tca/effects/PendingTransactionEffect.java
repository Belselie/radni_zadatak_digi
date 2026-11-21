package com.example.TransferProject.tca.effects;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import com.example.TransferProject.tca.actions.FinishedTransactionAction;
import com.example.TransferProject.tca.actions.TransactionAction;
import com.example.TransferProject.tca.service.TransferBalanceService;

public class PendingTransactionEffect implements Effect {

    private TransactionRecordRepository transactionRecordRepository;
    private TransactionState transactionState;
    private TransferBalanceService transferBalanceService;

    public PendingTransactionEffect(TransactionRecordRepository transactionRecordRepository, TransactionState transactionState, TransferBalanceService transferBalanceService){
        this.transactionRecordRepository=transactionRecordRepository;
        this.transactionState = transactionState;
        this.transferBalanceService = transferBalanceService;
    }

    @Override
    public TransactionAction run() {

        TransactionState tempState;

        tempState = transferBalanceService.transferFunds(transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd());

        if (tempState.status() == TransactionStatus.SUCCESS){
            TransactionRecord t = new TransactionRecord(transactionState.transactionId(), transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
            transactionRecordRepository.save(t);
            return new FinishedTransactionAction(TransactionStatus.SUCCESS, null);
        }

        else {
            TransactionRecord t = new TransactionRecord(transactionState.transactionId(), transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
            transactionRecordRepository.save(t);
            return new FinishedTransactionAction(TransactionStatus.FAILED,tempState.error());
        }

    }
}
