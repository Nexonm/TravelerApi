package com.example.SpringBootNetTry.exception.user;

public class UserDoeNottExistsException extends Exception{
    private static final String message = "Пользователя не существует";

    public UserDoeNottExistsException(){
        super(message);
    }
}
