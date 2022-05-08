package com.example.SpringBootNetTry.entity;

import javax.persistence.*;

//грубо говоря это табличный класс
//аннотация нужна, чтобы JPA сделал таблицу из класса
@Entity
@Table(name = "cards")
public class CardEntity {

    //Column names
    private final static String COL_CARD_ID = "card_ID";
    protected final static String COL_PERSON = "user";
    private final static String COL_CITY = "city";
    private final static String COL_COUNTRY = "country";
    private final static String COL_FULL_DESCRIPTION = "full_description";
    private final static String COL_SHORT_DESCRIPTION = "short_description";
    private final static String COL_ADDRESS = "address";
    private final static String COL_PATH_TO_PHOTO = "path_to_photo";
    private final static String COL_IS_PAYMENT_FIXED = "is_payment_fixed";
    private final static String COL_COST = "cost";
    private final static String COL_IS_MALE = "sex_Is_male";
    private final static String COL_HASHTAG = "hashtag";


    //Column fields

    //показываем, что это первичный ключ
    //указываем способ генерации ключа, тут автоинкремент
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = COL_CARD_ID)
    private long ID;

    //PersonID - ID человека, создавшего карточку, чтобы связать две сущности используем @ForeignKey
    @ManyToOne
    //@JoinColumn(foreignKey = @ForeignKey(name = PersonEntity.COL_PERSON_ID))
    @JoinColumn(name = UserEntity.COL_USER_ID)
    //@Column(name = COL_PERSON)
    private UserEntity user;

    @Column(name = COL_CITY)
    private String city;

    @Column(name = COL_COUNTRY)
    private String country;

    @Column(name = COL_FULL_DESCRIPTION)
    private String fullDescription;

    @Column(name = COL_SHORT_DESCRIPTION)
    private String shortDescription;

    @Column(name = COL_ADDRESS)
    private String address; //address is street and house

    @Column(name = COL_PATH_TO_PHOTO)
    private String pathToPhoto;//later we will have photos

    @Column(name = COL_IS_PAYMENT_FIXED)
    private boolean isPaymentFixed;

    @Column(name = COL_COST)
    private int cost;

    @Column(name = COL_IS_MALE)
    private boolean male;

    @Column(name = COL_HASHTAG)
    private String hashtag;


    //constructors

    public CardEntity() {

    }

    public CardEntity(
            long ID,
            UserEntity user,
            String city,
            String country,
            String fullDescription,
            String shortDescription,
            String address,
            String pathToPhoto,
            boolean isPaymentFixed,
            int cost,
            boolean male,
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
        this.isPaymentFixed = isPaymentFixed;
        this.cost = cost;
        this.male = male;
        this.hashtag = hashtag;
    }


    //Override methods
    @Override
    public String toString() {
        return COL_CARD_ID + ": " + getID() + ", " +
                COL_PERSON + ": " + getUser() + ", " +
                COL_CITY + ": " + getCity() + ", " +
                COL_COUNTRY + ": " + getCountry() + ", " +
                COL_FULL_DESCRIPTION + ": " + getFullDescription() + ", " +
                COL_SHORT_DESCRIPTION + ": " + getShortDescription() + ", " +
                COL_ADDRESS + ": " + getAddress() + ", " +
                COL_PATH_TO_PHOTO + ": " + getPathToPhoto() + ", " +
                COL_IS_PAYMENT_FIXED + ": " + isPaymentFixed() + ", " +
                COL_COST + ": " + getCost() + ", " +
                COL_IS_MALE + ": " + isIs_male();
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

    public boolean isPaymentFixed() {
        return isPaymentFixed;
    }

    public void setPaymentFixed(boolean paymentFixed) {
        isPaymentFixed = paymentFixed;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isIs_male() {
        return male;
    }

    public void setIs_male(boolean is_male) {
        this.male = is_male;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }
}
