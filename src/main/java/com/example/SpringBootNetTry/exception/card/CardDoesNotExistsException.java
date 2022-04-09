package com.example.SpringBootNetTry.exception.card;

public class CardDoesNotExistsException extends Exception{
    private static final String message = "Карты с таким id не существует";

    public CardDoesNotExistsException() {
        super(message);
    }
}
