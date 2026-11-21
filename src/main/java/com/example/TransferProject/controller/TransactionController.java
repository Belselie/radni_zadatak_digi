package com.example.TransferProject.controller;

import com.example.TransferProject.dto.TransactionRequest;
import com.example.TransferProject.tca.StoreNew;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import com.example.TransferProject.tca.actions.DraftTransactionAction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final ObjectProvider<StoreNew> stores;

    public TransactionController(ObjectProvider<StoreNew> stores){
        this.stores = stores;
    }

    @PostMapping
    public ResponseEntity<TransactionStatus> transfer(@RequestHeader(value = "x-transaction-id", required = true) String transactionId,
                                                      @Valid @RequestBody TransactionRequest request) {

        TransactionState state;
        StoreNew store = stores.getObject();

        if (transactionId == null || transactionId.isBlank()){
            System.out.println("No transaction id");
            return ResponseEntity.badRequest().body(TransactionStatus.FAILED);
        }

        if(Objects.equals(request.fromUserAccountId(),request.toUserAccountId())){
            System.out.println("Accounts are the same");
            return ResponseEntity.badRequest().body(TransactionStatus.FAILED);
        }

        if(request.amount_rsd().compareTo(BigDecimal.ZERO)<0){
            System.out.println("Negative amount");
            return ResponseEntity.badRequest().body(TransactionStatus.FAILED);
        }

        state = store.send(new DraftTransactionAction(request.fromUserAccountId(), request.toUserAccountId(), request.amount_rsd(), transactionId));

        return ResponseEntity.ok(state.status());

    }
}
