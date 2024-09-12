package com.neoteric.fullstackdemo_31._8._4.exception;

public class AccountCreationFailedException extends Exception{
    public String message;
    public AccountCreationFailedException(String msg){
        this.message=msg;
    }
}
