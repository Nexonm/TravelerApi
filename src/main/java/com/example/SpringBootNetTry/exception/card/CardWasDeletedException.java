package com.example.SpringBootNetTry.exception.card;

public class CardWasDeletedException extends Exception{
    private static final String message = "Карта с таким id была удалена";

    public CardWasDeletedException() {
        super(message);
    }
}
