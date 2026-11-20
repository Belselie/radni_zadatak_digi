package com.example.TransferProject.tca.reducer;

import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.effects.Effect;

public record TransactionReducerResult(
        TransactionStateNew state,
        Effect effect
) {
}
