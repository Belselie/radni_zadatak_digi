package com.example.TransferProject.exceptions;

public class SameUsersException extends RuntimeException{

    public  SameUsersException(Long id, Long id2){
        super("Users " +id+" and "+id2+" are the same.");
    }
}
