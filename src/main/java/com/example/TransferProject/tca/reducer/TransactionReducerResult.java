package com.example.TransferProject.tca.reducer;

import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.effects.Effect;

public record TransactionReducerResult(
        TransactionState state,
        Effect effect
) {
}
