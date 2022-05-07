package com.example.SpringBootNetTry.exception.user;

public class UserDataFormatException extends Exception{
    private static final String message = "Данные не соответствуют ожидаемому формату";
    public UserDataFormatException() {
        super(message);
    }
}
