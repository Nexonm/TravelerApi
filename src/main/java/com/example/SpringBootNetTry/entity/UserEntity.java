package com.example.SpringBootNetTry.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    //column names
    protected final static String COL_USER_ID = "ID";
    private final static String COL_USER_PASSWORD = "password";
    private final static String COL_FIRST_NAME = "First_name";
    private final static String COL_SECOND_NAME = "Second_name";
    private final static String COL_EMAIL = "Email";
    private final static String COL_PHONE_NUMBER = "Phone_number";
    private final static String COL_SOCIAL_CONTACTS = "Social_contacts";
    private final static String COL_PATH_TO_PHOTO = "Path_to_photo";
    private final static String COL_DATE_OF_BIRTH = "Date_of_birth";
    private final static String COL_IS_MALE = "Sex_Is_male";
    private final static String COL_USER_CARDS = "user_cards";
    private final static String COL_USER_FAVORITE_CARDS = "user_favorite_cards";
    private final static String COL_INTERESTS = "interests";
    private final static String COL_CHARACTERISTICS = "characteristics";

    public final static long UNDEFINED_ID = -1;


    //column fields

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COL_USER_ID)
    private long ID;

    @Column(name = COL_USER_PASSWORD)
    private String password;

    @Column(name = COL_FIRST_NAME)
    private String firstName;

    @Column(name = COL_SECOND_NAME)
    private String secondName;

    @Column(name = COL_EMAIL)
    private String email;

    @Column(name = COL_PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = COL_SOCIAL_CONTACTS)
    private String socialContacts;

    @Column(name = COL_PATH_TO_PHOTO)
    private String pathToPhoto;

    @Column(name = COL_DATE_OF_BIRTH)
    private String dateOfBirth;

    @Column(name = COL_INTERESTS)
    private String interests;

    @Column(name = COL_CHARACTERISTICS)
    private String characteristics;

    @Column(name = COL_IS_MALE)
    private boolean isMale;

    //cascade значит, что при удалении пользователя,
    //удаляются все созданные им карточки
    @Column(name = COL_USER_CARDS)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<CardEntity> userCards;

    @Column(name = COL_USER_FAVORITE_CARDS)
    private ArrayList<Long> userFavoriteCards;


    //constructors

    //default constructor for Spring
    public UserEntity() {

    }

    //registration constructor
    public UserEntity(
            String password,
            String email,
            String firstName,
            String secondName,
            String dateOfBirth
    ) {
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    //for models to save
    public UserEntity(
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            boolean isMale,
            List<CardEntity> userCards,
            ArrayList<Long> userFavoriteCards
    ) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialContacts = socialContacts;
        this.pathToPhoto = pathToPhoto;
        this.dateOfBirth = dateOfBirth;
        this.isMale = isMale;
        this.userCards = userCards;
        this.userFavoriteCards = userFavoriteCards;
    }

    //full constructor
    public UserEntity(
            String password,
            String firstName,
            String secondName,
            String email,
            String phoneNumber,
            String socialContacts,
            String pathToPhoto,
            String dateOfBirth,
            String interests,
            String characteristics,
            boolean isMale,
            List<CardEntity> userCards,
            ArrayList<Long> userFavoriteCards
    ) {
        this.password = password;
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.socialContacts = socialContacts;
        this.pathToPhoto = pathToPhoto;
        this.dateOfBirth = dateOfBirth;
        this.interests = interests;
        this.characteristics = characteristics;
        this.isMale = isMale;
        this.userCards = userCards;
        this.userFavoriteCards = userFavoriteCards;
    }

    //Override methods
    @Override
    public String toString() {
        return COL_USER_ID + ": " + getID() + ", " +
                COL_FIRST_NAME + ": " + getFirstName() + ", " +
                COL_SECOND_NAME + ": " + getSecondName() + ", " +
                COL_EMAIL + ": " + getEmail() + ", " +
                COL_PHONE_NUMBER + ": " + getPhoneNumber() + ", " +
                COL_SOCIAL_CONTACTS + ": " + getSocialContacts() + ", " +
                COL_PATH_TO_PHOTO + ": " + getPathToPhoto() + ", " +
                COL_DATE_OF_BIRTH + ": " + getDateOfBirth() + ", " +
                COL_IS_MALE + ": " + isMale() + ", " +
                COL_USER_CARDS + ": " + getUserCards() + ", " +
                COL_USER_FAVORITE_CARDS + ": " + getUserFavoriteCards();
    }


    //methods

    public void combine(UserEntity aUser) {
        if (this.phoneNumber == null) this.setPhoneNumber(aUser.phoneNumber);
        if (this.socialContacts == null) this.setSocialContacts(aUser.socialContacts);
        this.setMale(aUser.isMale);
    }


    //Getters and Setters

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public List<CardEntity> getUserCards() {
        return userCards;
    }

    //as we work with database we need to just add new card to user
    public void setUserCards(CardEntity userCards) {
        if (this.userCards == null)
            this.userCards = new ArrayList<CardEntity>();
        if (userCards != null)
            this.userCards.add(userCards);
    }


    /**
     * It is not allowed to delete card from card list. Method needed to protect such deletion.
     *
     * @param cid card id
     */
    public void deleteCardByIdFromUsersCards(long cid) {

    }

    public ArrayList<Long> getUserFavoriteCards() {
        return userFavoriteCards;
    }

    public void setUserFavoriteCards(ArrayList<Long> userFavoriteCards) {
        this.userFavoriteCards = userFavoriteCards;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

}