package com.example.SpringBootNetTry.exception.post;

public class PostDoesNotExistsException extends Exception{
    private static final String message = "Поста с таким id не существует";

    public PostDoesNotExistsException() {
        super(message);
    }
}
