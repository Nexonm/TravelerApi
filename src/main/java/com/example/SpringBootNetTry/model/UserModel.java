package com.example.SpringBootNetTry.model;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.entity.UserEntity;

import java.util.ArrayList;
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
    private List<CardModel> userCards;
    private ArrayList<Long> userFavoriteCards;


    //methods

    //main method

    /***
     *This method returns representation model of entity
     * it hides some fields and gives only allowed information
     * @param entity user entity from database
     * @param withCards true -> show user's cards, false -> hide user's cards
     * @return representation model of userEntity
     */
    public static UserModel toUserModel(UserEntity entity, boolean withCards) {
        UserModel model = new UserModel();
        model.setID(entity.getID());
        model.setFirstName(entity.getFirstName());
        model.setSecondName(entity.getSecondName());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setSocialContacts(entity.getSocialContacts());
        model.setPathToPhoto(entity.getPathToPhoto());
        model.setDateOfBirth(entity.getDateOfBirth());
        model.setMale(entity.isMale());
        model.setUserCards(entity.getUserCards(), withCards);

        return model;
    }


    //constructors

    public UserModel() {
    }

    private UserModel(
            long ID,
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            boolean male,
            ArrayList<Long> userFavoriteCards
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

    public List<CardModel> getUserCards() {
        return userCards;
    }

    public void setUserCards(List<CardEntity> userCards, boolean withCards) {
        this.userCards = new ArrayList<>();
        if (withCards) {
            for (CardEntity entity : userCards) {
                this.userCards.add(CardModel.toCardModel(entity, false));
            }
        }
    }

    public ArrayList<Long> getUserFavoriteCards() {
        return userFavoriteCards;
    }

    public void setUserFavoriteCards(ArrayList<Long> userFavoriteCards) {
        this.userFavoriteCards = userFavoriteCards;
    }
}
