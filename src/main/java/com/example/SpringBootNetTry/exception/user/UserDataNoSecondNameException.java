package com.example.SpringBootNetTry.exception.user;

public class UserDataNoSecondNameException extends Exception{
    private static final String message = "Регистрация невозможна: не предоставлены данные о ФАМИЛИИ";
    public UserDataNoSecondNameException() {
        super(message);
    }
}
