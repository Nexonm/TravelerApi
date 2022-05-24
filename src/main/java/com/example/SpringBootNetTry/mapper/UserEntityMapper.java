package com.example.SpringBootNetTry.mapper;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.model.CardModel;
import com.example.SpringBootNetTry.model.UserModel;
import com.google.gson.Gson;

import java.util.ArrayList;


public class UserEntityMapper {
    /***
     *This method returns representation model of entity
     * it hides some fields and gives only allowed information
     * @param user user entity from database
     * @param withCards true -> show user's cards, false -> hide user's cards
     * @return representation model of userEntity
     */
    public static UserModel toUserModel(UserEntity user, boolean withCards) {
        ArrayList<CardModel> models = new ArrayList<>();
        if (withCards) {
            if (user.getUserCards() != null)
                for (CardEntity entity : user.getUserCards()) {
                    models.add(CardEntityMapper.toCardModel(entity, false));
                }
        }
        return new UserModel(
                user.getID() + 0,
                user.getFirstName() + "",
                user.getSecondName() + "",
                user.getEmail() + "",
                user.getPhoneNumber() + "",
                user.getSocialContacts() + "",
                user.getPathToPhoto() + "",
                user.getDateOfBirth() + "",
                user.isMale(),
                (new Gson()).toJson(models) + "",
                (user.getUserFavoriteCards() != null) ? //check if userFavoriteCards are null
                        (new Gson()).toJson(user.getUserFavoriteCards()) + "" :
                        (new Gson()).toJson(new ArrayList<Long>()) + "",
                user.getInterests() + "",
                user.getCharacteristics() + ""
        );
    }

    public static UserEntity toUserEntity(String strGson) {
        try {
            UserEntity entity = new Gson().fromJson(strGson, UserEntity.class);

            return entity;
        } catch (Exception e) {
            return null;
        }

    }
}
