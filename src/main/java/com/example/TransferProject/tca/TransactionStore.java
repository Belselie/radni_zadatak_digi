package com.example.TransferProject.tca;

import com.example.TransferProject.dto.TransactionResponse;
import com.example.TransferProject.service.TransactionService;
import org.springframework.stereotype.Component;

@Component
public class TransactionStore {

    private final TransactionService transactionService;
    private  TransactionState state = TransactionState.ready();

    public TransactionStore(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    public TransactionResponse dispatch(TransactionAction.InProgress inProgress) {
        state = TransactionReducer.reduce(state, inProgress);

        try {
            TransactionResponse transactionResponse = transactionService.executeTransaction(inProgress.getFromUserId(), inProgress.getToUserId(), inProgress.getAmountRsd());
            return transactionResponse;
        } catch (RuntimeException ex){
            state = TransactionReducer.reduce(state, new TransactionAction.Failed(ex.getMessage()));
            throw ex;
        }
    }
    public TransactionState getState(){
        return state;
    }
}
