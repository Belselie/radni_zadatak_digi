package com.example.TransferProject.tca.reducer;

import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.repository.UserRepository;
import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.TransactionStatusNew;
import com.example.TransferProject.tca.actions.*;
import com.example.TransferProject.tca.effects.CreateTransactionEffect;
import com.example.TransferProject.tca.effects.DraftTransactionEffect;
import com.example.TransferProject.tca.effects.FinishedTransactionEffect;
import com.example.TransferProject.tca.effects.PendingTransactionEffect;
import org.springframework.stereotype.Component;

@Component
public class TransactionReducer {
    public TransactionRecordRepository transactionRecordRepository;
    public UserRepository userRepository;

    public TransactionReducer(TransactionRecordRepository transactionRecordRepository, UserRepository userRepository){
        this.transactionRecordRepository=transactionRecordRepository;
        this.userRepository=userRepository;
    }

    public TransactionReducerResult reduce(TransactionStateNew state, TransactionActionNew action) {
        TransactionStateNew transaction;
        TransactionReducerResult result;
        switch(action) {
            case DraftTransactionAction draftTransactionAction: {

                transaction = new TransactionStateNew(draftTransactionAction.fromAccountId(), draftTransactionAction.toAccountId(), draftTransactionAction.amount_rsd(), TransactionStatusNew.DRAFT, null);

                result = new TransactionReducerResult(transaction, new DraftTransactionEffect(transaction));

                break;
            }

            case CreateTransactionAction createTransactionAction: {

                transaction = new TransactionStateNew(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionStatusNew.CREATED, null);

                result = new TransactionReducerResult(transaction, new CreateTransactionEffect(transactionRecordRepository, transaction));
                System.out.println("Returned CreateTransactionEffect. State is: " + transaction.status());
                break;
            }

            case PendingTransactionAction pendingTransactionAction: {

                transaction = new TransactionStateNew(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionStatusNew.PENDING, null);

                result = new TransactionReducerResult(transaction, new PendingTransactionEffect(userRepository,transactionRecordRepository, transaction));
                System.out.println("Returned PendingTransactionEffect. State is: " + transaction.status());
                break;
            }

            case FinishedTransactionAction finishedTransactionAction: {

                transaction = new TransactionStateNew(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), finishedTransactionAction.status(), finishedTransactionAction.error());

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
