package com.example.TransferProject.service;

import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.repository.TransactionRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionRecordService {

    private final TransactionRecordRepository transactionrepository;

    public TransactionRecordService(TransactionRecordRepository transactionrepository){
        this.transactionrepository = transactionrepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordSuccess(Long fromUserId, Long toUserId, BigDecimal amount){

        transactionrepository.save(new TransactionRecord(fromUserId, toUserId, amount, TransactionRecord.Status.SUCCESS, null));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFail(Long fromUserId, Long toUserId, BigDecimal amount, String failureReason){

        transactionrepository.save(new TransactionRecord(fromUserId, toUserId, amount, TransactionRecord.Status.FAILED, failureReason));

    }

}
