package com.hcmute.baloshop.exceptions;

public class AlreadyExistException extends RuntimeException{
    public AlreadyExistException(){

    }
    public AlreadyExistException(String message){
        super(message);
    }
}
