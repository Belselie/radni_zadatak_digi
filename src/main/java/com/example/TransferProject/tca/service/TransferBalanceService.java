package com.example.TransferProject.tca.service;

import com.example.TransferProject.exceptions.InsufficientBalanceException;
import com.example.TransferProject.exceptions.UserNotFoundException;
import com.example.TransferProject.orm.User;
import com.example.TransferProject.repository.UserRepository;
import com.example.TransferProject.tca.TransactionState;
import com.example.TransferProject.tca.TransactionStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferBalanceService {

    private UserRepository userRepository;

    public TransferBalanceService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional
    public TransactionState transferFunds(Long fromUserAccountId, Long toUserAccountId, BigDecimal amount_rsd){
        try {

            User fromUser = userRepository.findByIdWithLock(fromUserAccountId).orElseThrow(() -> new UserNotFoundException(fromUserAccountId));
            User toUser = userRepository.findByIdWithLock(toUserAccountId).orElseThrow(() -> new UserNotFoundException(toUserAccountId));

            if (fromUser.getBalanceRsd().compareTo(amount_rsd) < 0){
                throw new InsufficientBalanceException(fromUserAccountId);
            }

            fromUser.setBalance(fromUser.getBalanceRsd().subtract(amount_rsd));
            toUser.setBalance(toUser.getBalanceRsd().add(amount_rsd));

            userRepository.save(fromUser);
            userRepository.save(toUser);

            return new TransactionState(fromUserAccountId,toUserAccountId,amount_rsd,TransactionStatus.SUCCESS,null,null);
        } catch (UserNotFoundException | InsufficientBalanceException ex){

            if (ex instanceof UserNotFoundException) {
                return new TransactionState(fromUserAccountId,toUserAccountId,amount_rsd,TransactionStatus.FAILED,"One of the users was not found",null);

            }

            else if (ex instanceof InsufficientBalanceException) {
                return new TransactionState(fromUserAccountId,toUserAccountId,amount_rsd,TransactionStatus.FAILED,"Not enough funds on account",null);

            }
            else {
                return new TransactionState(fromUserAccountId,toUserAccountId,amount_rsd,TransactionStatus.FAILED,null,null);
            }
        }
    }
}
