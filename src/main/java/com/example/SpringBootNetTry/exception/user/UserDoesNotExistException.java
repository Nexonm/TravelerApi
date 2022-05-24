package com.example.SpringBootNetTry.exception.user;

public class UserDoesNotExistException extends Exception{
    private static final String message = "Пользователя не существует";

    public UserDoesNotExistException(){
        super(message);
    }
}
