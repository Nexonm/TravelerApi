package com.example.SpringBootNetTry.exception.user;

public class UserDataNoFirstNameException extends Exception{
    private static final String message = "Регистрация невозможна: не предоставлены данные об ИМЕНИ";
    public UserDataNoFirstNameException() {
        super(message);
    }
}
