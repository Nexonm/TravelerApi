package com.example.SpringBootNetTry.exception.user;

public class UserAlreadyExistsException extends Exception{
    private static final String message = "Пользователь с таким email уже существует";
    public UserAlreadyExistsException() {
        super(message);
    }
}
