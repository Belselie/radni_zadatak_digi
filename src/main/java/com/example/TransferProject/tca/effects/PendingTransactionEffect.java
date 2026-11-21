package com.example.TransferProject.tca.effects;

import com.example.TransferProject.exceptions.InsufficientBalanceException;
import com.example.TransferProject.exceptions.UserNotFoundException;
import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.orm.User;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.repository.UserRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import com.example.TransferProject.tca.actions.FinishedTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class PendingTransactionEffect implements Effect {

    private UserRepository userRepository;
    private TransactionRecordRepository transactionRecordRepository;
    private TransactionState transactionState;

    public PendingTransactionEffect(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, TransactionState transactionState){
        this.userRepository=userRepository;
        this.transactionRecordRepository=transactionRecordRepository;
        this.transactionState = transactionState;
    }

    @Override
    public TransactionActionNew run() {
        System.out.println("Started Pending");
        try {
            System.out.println("Entered Pending Try");
            User fromUser = userRepository.findByIdWithLock(transactionState.fromUserAccountId()).orElseThrow(() -> new UserNotFoundException(transactionState.fromUserAccountId()));
            User toUser = userRepository.findByIdWithLock(transactionState.toUserAccountId()).orElseThrow(() -> new UserNotFoundException(transactionState.toUserAccountId()));

            if (fromUser.getBalanceRsd().compareTo(transactionState.amount_rsd()) < 0){
                throw new InsufficientBalanceException(transactionState.fromUserAccountId());
            }

            fromUser.setBalance(fromUser.getBalanceRsd().subtract(transactionState.amount_rsd()));
            toUser.setBalance(toUser.getBalanceRsd().add(transactionState.amount_rsd()));

            userRepository.save(fromUser);
            userRepository.save(toUser);

            TransactionRecord t = new TransactionRecord(transactionState.fromUserAccountId(), transactionState.toUserAccountId(), transactionState.amount_rsd(), transactionState.status(), transactionState.error());
            transactionRecordRepository.save(t);

            System.out.println("Pending ok");
            return new FinishedTransactionAction(TransactionStatus.SUCCESS, null);

        } catch (UserNotFoundException | InsufficientBalanceException ex){
            System.out.println("Pending catch");

            return new FinishedTransactionAction(TransactionStatus.FAILED,"Transfer of funds has failed");
        }


    }
}
