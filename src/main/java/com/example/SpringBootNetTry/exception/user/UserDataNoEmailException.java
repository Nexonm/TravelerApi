package com.example.SpringBootNetTry.exception.user;

public class UserDataNoEmailException extends Exception{
    private static final String message = "Регистрация невозможна: не предоставлены данные email";
    public UserDataNoEmailException() {
        super(message);
    }
}
