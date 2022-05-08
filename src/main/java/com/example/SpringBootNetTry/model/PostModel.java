package com.example.SpringBootNetTry.model;

public class PostModel {

    //fields

    private long ID;
    private UserModel user;
    private String description;
    private String pathToPhoto;
    private String hashtag;


    //constructors

    public PostModel(
            long ID,
            UserModel user,
            String description,
            String pathToPhoto,
            String hashtag
    ) {
        this.ID = ID;
        this.user = user;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
