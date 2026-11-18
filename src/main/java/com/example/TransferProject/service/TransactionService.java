package com.example.TransferProject.service;


import com.example.TransferProject.orm.User;
import com.example.TransferProject.dto.TransactionResponse;
import com.example.TransferProject.exceptions.InsufficientBalanceException;
import com.example.TransferProject.exceptions.SameUsersException;
import com.example.TransferProject.exceptions.UserNotFoundException;
import com.example.TransferProject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final UserRepository userRepository;
    private final TransactionRecordService recordService;


    public TransactionService (UserRepository userRepository, TransactionRecordService recordService){
        this.userRepository = userRepository;
        this.recordService = recordService;
    }

    @Transactional
    public TransactionResponse executeTransaction(Long fromUserAccountId, Long toUserAccountId, BigDecimal amount_rsd){

        if (fromUserAccountId.equals(toUserAccountId)){
            recordService.recordFail(fromUserAccountId, toUserAccountId, amount_rsd, "Source and destination accounts are the same");
            throw new SameUsersException(fromUserAccountId,toUserAccountId);
        }

        try {
            User fromUser = userRepository.findByIdWithLock(fromUserAccountId).orElseThrow(() -> new UserNotFoundException(fromUserAccountId));
            User toUser = userRepository.findByIdWithLock(toUserAccountId).orElseThrow( () -> new UserNotFoundException(toUserAccountId));

            if (fromUser.getBalanceRsd().compareTo(amount_rsd) < 0){
                throw new InsufficientBalanceException(fromUser.getId());
            }

            fromUser.setBalance(fromUser.getBalanceRsd().subtract(amount_rsd));
            toUser.setBalance(toUser.getBalanceRsd().add(amount_rsd));
            userRepository.save(fromUser);
            userRepository.save(toUser);

            recordService.recordSuccess(fromUser.getId(), toUser.getId(), amount_rsd);

            return new TransactionResponse(fromUser.getId(), toUser.getId(), amount_rsd, "success");

        } catch (UserNotFoundException | InsufficientBalanceException ex) {
            recordService.recordFail(fromUserAccountId, toUserAccountId, amount_rsd, ex.getMessage());
            throw ex;
        }

    }
}
