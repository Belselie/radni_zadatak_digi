package com.example.TransferProject.tca.reducer;

import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.repository.UserRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import com.example.TransferProject.tca.actions.*;
import com.example.TransferProject.tca.effects.CreateTransactionEffect;
import com.example.TransferProject.tca.effects.DraftTransactionEffect;
import com.example.TransferProject.tca.effects.FinishedTransactionEffect;
import com.example.TransferProject.tca.effects.PendingTransactionEffect;
import com.example.TransferProject.tca.service.TransferBalanceService;
import org.springframework.stereotype.Component;

@Component
public class TransactionReducer {
    public TransactionRecordRepository transactionRecordRepository;
    public UserRepository userRepository;
    public TransferBalanceService transferBalanceService;

    public TransactionReducer(TransactionRecordRepository transactionRecordRepository, UserRepository userRepository, TransferBalanceService transferBalanceService){
        this.transactionRecordRepository=transactionRecordRepository;
        this.userRepository=userRepository;
        this.transferBalanceService=transferBalanceService;
    }

    public TransactionReducerResult reduce(TransactionState state, TransactionAction action) {
        TransactionState transaction;
        TransactionReducerResult result;
        switch(action) {
            case DraftTransactionAction draftTransactionAction: {

                transaction = new TransactionState(draftTransactionAction.fromAccountId(), draftTransactionAction.toAccountId(), draftTransactionAction.amount_rsd(), TransactionStatus.DRAFT, null, draftTransactionAction.transactionId());

                result = new TransactionReducerResult(transaction, new DraftTransactionEffect());

                break;
            }

            case CreateTransactionAction createTransactionAction: {

                transaction = new TransactionState(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionStatus.CREATED, null, state.transactionId());

                result = new TransactionReducerResult(transaction, new CreateTransactionEffect(transactionRecordRepository, transaction));
                System.out.println("Returned CreateTransactionEffect. State is: " + transaction.status());
                break;
            }

            case PendingTransactionAction pendingTransactionAction: {

                transaction = new TransactionState(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionStatus.PENDING, null, state.transactionId());

                result = new TransactionReducerResult(transaction, new PendingTransactionEffect(transactionRecordRepository, transaction, transferBalanceService));
                System.out.println("Returned PendingTransactionEffect. State is: " + transaction.status());
                break;
            }

            case FinishedTransactionAction finishedTransactionAction: {

                transaction = new TransactionState(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), finishedTransactionAction.status(), finishedTransactionAction.error(), state.transactionId());

                result = new TransactionReducerResult(transaction, new FinishedTransactionEffect(transactionRecordRepository, transaction));

                System.out.println("Returned FinishedTransactionEffect. State is: " + transaction.status());

                break;
            }

            default:
                throw new IllegalStateException("Unexpected value: " + action);
        }
        return result;
    }
}
