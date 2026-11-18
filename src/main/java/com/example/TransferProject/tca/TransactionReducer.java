package com.example.TransferProject.tca;

public class TransactionReducer {

    public static TransactionState reduce(TransactionState state, TransactionAction action){

        return switch (action) {
            case TransactionAction.InProgress ip -> new TransactionState(ip.getFromUserId(), ip.getToUserId(), ip.getAmountRsd(), TransactionState.Status.IN_PROGRESS, null);

            case TransactionAction.Succeeded succeded -> new TransactionState(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionState.Status.SUCCESS, null);

            case TransactionAction.Failed failed -> new TransactionState(state.fromUserAccountId(), state.toUserAccountId(), state.amount_rsd(), TransactionState.Status.FAILED, failed.getFailureReason());

//            TODO proper exception handling
            default -> throw new IllegalStateException("Unexpected value: " + action);
        };

    }
}
