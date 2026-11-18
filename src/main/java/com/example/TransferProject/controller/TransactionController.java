package com.example.TransferProject.controller;

import com.example.TransferProject.dto.TransactionRequest;
import com.example.TransferProject.dto.TransactionResponse;
import com.example.TransferProject.tca.TransactionAction;
import com.example.TransferProject.tca.TransactionStore;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionStore store;

    public TransactionController(TransactionStore store){
        this.store = store;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransactionRequest request){
        TransactionResponse response = store.dispatch(new TransactionAction.InProgress(request.fromUserAccountId(), request.toUserAccountId(),request.amount_rsd()));
        return ResponseEntity.ok(response);
    }
}
