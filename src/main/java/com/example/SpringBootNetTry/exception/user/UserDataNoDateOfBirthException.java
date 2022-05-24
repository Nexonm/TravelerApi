package com.example.SpringBootNetTry.exception.user;

public class UserDataNoDateOfBirthException extends Exception{
    private static final String message = "Регистрация невозможна: не предоставлены данные о дате рождения";
    public UserDataNoDateOfBirthException() {
        super(message);
    }
}
