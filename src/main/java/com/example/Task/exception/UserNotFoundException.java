package com.example.Task.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){

        super(message);


    }
}