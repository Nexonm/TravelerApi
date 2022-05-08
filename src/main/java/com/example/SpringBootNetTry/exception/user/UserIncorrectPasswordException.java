package com.example.SpringBootNetTry.exception.user;

public class UserIncorrectPasswordException extends Exception{
    private static final String message = "Пароль не соответствует, вход невозможен";

    public UserIncorrectPasswordException(){
        super(message);
    }
}
