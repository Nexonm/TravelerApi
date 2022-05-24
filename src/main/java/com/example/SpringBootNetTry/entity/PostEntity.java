package com.example.SpringBootNetTry.entity;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class PostEntity {

    //Column names
    private final static String COL_POST_ID = "post_ID";
    protected final static String COL_USER = "user";
    private final static String COL_PATH_TO_PHOTO = "path_to_photo";
    private final static String COL_DESCRIPTION = "description";
    private final static String COL_HASHTAG = "hashtag";


    //Column fields

    //показываем, что это первичный ключ
    //указываем способ генерации ключа, тут автоинкремент
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COL_POST_ID)
    private long ID;

    //UUSER_ID - ID человека, создавшего карточку, чтобы связать две сущности используем @ForeignKey
    @ManyToOne
    //@JoinColumn(foreignKey = @ForeignKey(name = PersonEntity.COL_PERSON_ID))
    @JoinColumn(name = UserEntity.COL_USER_ID)
    //@Column(name = COL_USER)
    //not as a column in db... (ask google)
    private UserEntity user;

    @Column(name = COL_PATH_TO_PHOTO)
    private String pathToPhoto;

    @Column(name = COL_DESCRIPTION)
    private String description;

    @Column(name = COL_HASHTAG)
    private String hashtag;


    //Override methods
    @Override
    public String toString() {
        return  COL_POST_ID + ": " + getID() + ", " +
                COL_USER + ": " + getUser() + ", " +
                COL_PATH_TO_PHOTO + ": " + getPathToPhoto() + ", " +
                COL_DESCRIPTION + ": " + getDescription() + ", " +
                COL_HASHTAG + ": " + getHashtag();
    }


    //Getters and Setters

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getPathToPhoto() {
        return pathToPhoto;
    }

    public void setPathToPhoto(String pathToPhoto) {
        this.pathToPhoto = pathToPhoto;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

}
