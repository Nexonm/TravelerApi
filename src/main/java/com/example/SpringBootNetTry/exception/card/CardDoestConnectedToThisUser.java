package com.example.SpringBootNetTry.exception.card;

public class CardDoestConnectedToThisUser extends Exception{
    private static final String message = "Карта с таким id не может быть удалена: принадлежит другому пользователю";

    public CardDoestConnectedToThisUser() {
        super(message);
    }
}
