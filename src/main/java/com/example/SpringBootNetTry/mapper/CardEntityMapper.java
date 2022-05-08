package com.example.SpringBootNetTry.mapper;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.model.CardModel;

public class CardEntityMapper {
    //TODO in case card is created by user it has -1 id (add UNDEFINED_ID to CardEntity), make check for this.
    public static CardModel toCardModel(CardEntity card, boolean withUser) {
        return new CardModel(
                card.getID() + 0,
                (withUser ? UserEntityMapper.toUserModel(card.getUser(), false) : null),
                card.getCity() + "",
                card.getCountry() + "",
                card.getFullDescription() + "",
                card.getShortDescription() + "",
                card.getAddress() + "",
                card.getPathToPhoto() + "",
                card.isPaymentFixed(),
                card.getCost() +0,
                card.isIs_male(),
                card.getHashtag()
                );
    }
}
