package com.example.TransferProject.controller;

import com.example.TransferProject.dto.TransactionRequest;
import com.example.TransferProject.tca.StoreNew;
import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.TransactionStatusNew;
import com.example.TransferProject.tca.actions.DraftTransactionAction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TransactionStatusNew> transfer(@Valid @RequestBody TransactionRequest request) {

        TransactionStateNew state;

        System.out.println("Got Req Body");
        StoreNew store = stores.getObject();

        if(Objects.equals(request.fromUserAccountId(),request.toUserAccountId())){
            System.out.println("Accounts are the same");
            return ResponseEntity.badRequest().body(TransactionStatusNew.FAILED);
        }

        if(request.amount_rsd().compareTo(BigDecimal.ZERO)<0){
            System.out.println("Negative amount");
            return ResponseEntity.badRequest().body(TransactionStatusNew.FAILED);
        }

        System.out.println("Entering store");
        state = store.send(new DraftTransactionAction(request.fromUserAccountId(), request.toUserAccountId(), request.amount_rsd()));

        System.out.println("return ready");
        return ResponseEntity.ok(state.status());

    }
}
