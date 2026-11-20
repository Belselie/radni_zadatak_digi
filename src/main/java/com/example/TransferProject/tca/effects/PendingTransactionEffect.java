package com.example.TransferProject.tca.effects;

import com.example.TransferProject.exceptions.InsufficientBalanceException;
import com.example.TransferProject.exceptions.UserNotFoundException;
import com.example.TransferProject.orm.TransactionRecord;
import com.example.TransferProject.orm.User;
import com.example.TransferProject.repository.TransactionRecordRepository;
import com.example.TransferProject.repository.UserRepository;
import com.example.TransferProject.tca.TransactionStateNew;
import com.example.TransferProject.tca.TransactionStatusNew;
import com.example.TransferProject.tca.actions.FinishedTransactionAction;
import com.example.TransferProject.tca.actions.TransactionActionNew;

public class PendingTransactionEffect implements Effect {

    private UserRepository userRepository;
    private TransactionRecordRepository transactionRecordRepository;
    private TransactionStateNew transactionStateNew;

    public PendingTransactionEffect(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository, TransactionStateNew transactionStateNew){
        this.userRepository=userRepository;
        this.transactionRecordRepository=transactionRecordRepository;
        this.transactionStateNew=transactionStateNew;
    }

    @Override
    public TransactionActionNew run() {
        System.out.println("Started Pending");
        try {
            System.out.println("Entered Pending Try");
            User fromUser = userRepository.findByIdWithLock(transactionStateNew.fromUserAccountId()).orElseThrow(() -> new UserNotFoundException(transactionStateNew.fromUserAccountId()));
            User toUser = userRepository.findByIdWithLock(transactionStateNew.toUserAccountId()).orElseThrow(() -> new UserNotFoundException(transactionStateNew.toUserAccountId()));

            if (fromUser.getBalanceRsd().compareTo(transactionStateNew.amount_rsd()) < 0){
                throw new InsufficientBalanceException(transactionStateNew.fromUserAccountId());
            }

            fromUser.setBalance(fromUser.getBalanceRsd().subtract(transactionStateNew.amount_rsd()));
            toUser.setBalance(toUser.getBalanceRsd().add(transactionStateNew.amount_rsd()));

            userRepository.save(fromUser);
            userRepository.save(toUser);

            TransactionRecord t = new TransactionRecord(transactionStateNew.fromUserAccountId(), transactionStateNew.toUserAccountId(), transactionStateNew.amount_rsd(), transactionStateNew.status(),transactionStateNew.error());
            transactionRecordRepository.save(t);

            System.out.println("Pending ok");
            return new FinishedTransactionAction(TransactionStatusNew.SUCCESS, null);

        } catch (UserNotFoundException | InsufficientBalanceException ex){
            System.out.println("Pending catch");

            return new FinishedTransactionAction(TransactionStatusNew.FAILED,"Transfer of funds has failed");
        }


    }
}
