package com.example.TransferProject.tca.effects;

import com.example.TransferProject.tca.actions.TransactionAction;

public interface Effect {
    public TransactionAction run();
    public static TransactionAction none() {
        return null;
    }
}
