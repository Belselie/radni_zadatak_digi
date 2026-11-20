package com.example.TransferProject.tca.effects;

import com.example.TransferProject.tca.actions.TransactionActionNew;

public interface Effect {
    public TransactionActionNew run();
    public static TransactionActionNew none() {
        return null;
    }
}
