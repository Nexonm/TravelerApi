package com.example.SpringBootNetTry.model;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.mapper.CardEntityMapper;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserModel {
    private long ID;
    private String firstName;
    private String secondName;
    private String email;
    private String phoneNumber;
    private String socialContacts;
    private String pathToPhoto;
    private String dateOfBirth;
    private boolean male;
    private String userCards;
    private String userFavoriteCards;

    //constructors

    public UserModel(
            long ID,
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            boolean male,
            String userCards,
            String userFavoriteCards
    ) {
        this.ID = ID;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialContacts = socialContacts;
        this.pathToPhoto = pathToPhoto;
        this.dateOfBirth = dateOfBirth;
        this.male = male;
        this.userCards = userCards;
        this.userFavoriteCards = userFavoriteCards;
    }


    //getters and setters


    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSocialContacts() {
        return socialContacts;
    }

    public void setSocialContacts(String socialContacts) {
        this.socialContacts = socialContacts;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getUserCards() {
        return userCards;
    }

    public void setUserCards(String userCards){
        this.userCards = userCards;
    }

    public void setUserCards(List<CardEntity> userCards, boolean withCards) {
        ArrayList<CardModel> models = new ArrayList<>();
        if (withCards) {
            for (CardEntity entity : userCards) {
                models.add(CardEntityMapper.toCardModel(entity, false));
            }
        }
        this.userCards = (new Gson()).toJson(models);
    }

    public String getUserFavoriteCards() {
        return userFavoriteCards;
    }

    public void setUserFavoriteCards(String userFavoriteCards){
        this.userFavoriteCards = userFavoriteCards;
    }

    public void setUserFavoriteCards(ArrayList<Long> userFavoriteCards) {
        this.userFavoriteCards = (new Gson()).toJson(userFavoriteCards);
    }
}
