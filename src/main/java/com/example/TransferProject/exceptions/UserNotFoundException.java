package com.example.TransferProject.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("Account not found " + id);
    }
}
