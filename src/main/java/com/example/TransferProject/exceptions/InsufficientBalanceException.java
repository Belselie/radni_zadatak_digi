package com.example.TransferProject.exceptions;

public class InsufficientBalanceException extends RuntimeException{

    public InsufficientBalanceException (Long id){
        super("The requested user "+id+" does not have enough funds for this transfer.");
    }
}
