package com.example.SpringBootNetTry.model;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.mapper.UserEntityMapper;
import com.example.SpringBootNetTry.model.UserModel;

public class CardModel {

    //fields

    private long ID;
    private UserModel user;
    private String city;
    private String country;
    private String fullDescription;
    private String shortDescription;
    private String address;
    private String pathToPhoto;
    private String hashtag;


    //methods



    //constructors

    public CardModel(
            long ID,
            UserModel user,
            String city,
            String country,
            String fullDescription,
            String shortDescription,
            String address,
            String pathToPhoto,
            String hashtag
    ) {
        this.ID = ID;
        this.user = user;
        this.city = city;
        this.country = country;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
        this.address = address;
        this.pathToPhoto = pathToPhoto;
        this.hashtag = hashtag;
    }


    //getters and setters

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
