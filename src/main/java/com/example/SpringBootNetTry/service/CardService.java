package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.card.CardDoesNotExistsException;
import com.example.SpringBootNetTry.exception.card.CardDoestConnectedToThisUser;
import com.example.SpringBootNetTry.exception.card.CardWasDeletedException;
import com.example.SpringBootNetTry.exception.user.UserDoesNotExistException;
import com.example.SpringBootNetTry.exception.user.UserIncorrectPasswordException;
import com.example.SpringBootNetTry.mapper.CardEntityMapper;
import com.example.SpringBootNetTry.mapper.UserEntityMapper;
import com.example.SpringBootNetTry.model.CardModel;
import com.example.SpringBootNetTry.model.UserModel;
import com.example.SpringBootNetTry.repository.CardRepo;
import com.example.SpringBootNetTry.repository.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//аннотация говорит, что этот класс ответственен за всю логику
@Service
public class CardService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private UserRepo userRepo;

    public CardModel makeCard(String gsonStr, long personId) throws UserDoesNotExistException {
        CardEntity card = (new Gson()).fromJson(gsonStr, CardEntity.class);
        UserEntity person = userRepo.findById(personId);
        if (person == null) throw new UserDoesNotExistException();
        card.setUser(person);
        //add one more card to user
        person.setUserCards(card);
        cardRepo.save(card);

        //TODO Сделать проверку на полную идентичность карт

        return CardEntityMapper.toCardModel(cardRepo.save(card), false);
    }

    public CardModel getOneCardById(long id)
            throws CardDoesNotExistsException,
            CardWasDeletedException {
        System.out.println("CARD_GET_BY_ID count = " + cardRepo.count());
        if (cardRepo.findById(id) == null) {
            if (getLastCardId() > id) {
                throw new CardWasDeletedException();
            } else throw new CardDoesNotExistsException();
        }
        return CardEntityMapper.toCardModel(cardRepo.findById(id), true);
    }

    public long getLastCardId() {
        long max = -1;
        for (CardEntity card : cardRepo.findAll()) {
            if (max < card.getID()) max = card.getID();
        }
        return max;
    }

    /**
     * Delete card by its id. Note: only user that created this card can delete it
     *
     * @param id        card id
     * @param userEmail user email
     * @param pass      user password
     * @return user with updated data
     * @throws CardDoesNotExistsException     in case id is wrong
     * @throws UserDoesNotExistException      in case user email is wrong
     * @throws CardDoestConnectedToThisUser   if someOne else trying to delete card
     * @throws UserIncorrectPasswordException in case pass is wrong
     */
    public UserModel deleteCardById(long id, String userEmail, String pass)
            throws CardDoesNotExistsException,
            UserDoesNotExistException,
            CardDoestConnectedToThisUser,
            UserIncorrectPasswordException {
        UserEntity user = userRepo.findByEmail(userEmail);
        if (user == null) throw new UserDoesNotExistException();
        CardEntity card = cardRepo.findById(id);
        if (card == null) throw new CardDoesNotExistsException();
        //we need protection, cause every one can delete card by request
        //note: only user can delete its card
        if (!user.getEmail().equals(card.getUser().getEmail())) throw new CardDoestConnectedToThisUser();
        try {
            String hex = String.format("%064x", new BigInteger(1,
                    MessageDigest.getInstance("SHA3-256").digest(
                            pass.getBytes(StandardCharsets.UTF_8)
                    )));
            //in case user password is incorrect
            if (!user.getPassword().equals(hex)) throw new UserIncorrectPasswordException();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //everything all right, we can delete card

        cardRepo.deleteById(card.getID());

        return UserEntityMapper.toUserModel(userRepo.save(user), true);
    }

    /**
     * Finds all cards by str from server
     *
     * @param str some str
     * @return list with card's ids
     */
    public ArrayList<Long> getListThreeSorted(String str) {
        Set<Long> set = new HashSet<>();
        set.addAll(getListByCity(str));
        set.addAll(getListByCountry(str));
        set.addAll(getListByHashtag(str));
        return new ArrayList<>(set);
    }

    /**
     * Finds all cards by city, uses ignoreCase
     *
     * @param city name of city
     * @return list with card's ids
     */
    public ArrayList<Long> getListByCity(String city) {
        //get all cards by city, uses ignoreCase
        CardEntity[] cardsArr = cardRepo.findByCity(city);
        CardEntity[] cardsArr1 = cardRepo.findByCity(city + " ");
        //empty arrayList as container for answer
        ArrayList<Long> cards = new ArrayList<>();
        ArrayList<Long> cards1 = new ArrayList<>();
        //fill arrayList
        for (CardEntity card : cardsArr)
            cards.add(card.getID());
        for (CardEntity card : cardsArr1)
            cards1.add(card.getID());
        Set<Long> set = new HashSet<>();
        set.addAll(cards);
        set.addAll(cards1);
        //return answer
        return new ArrayList<>(set);
    }

    /**
     * Finds all cards by country, uses ignoreCase
     *
     * @param country name of country
     * @return list with card's ids
     */
    public ArrayList<Long> getListByCountry(String country) {
        //get all cards by country, uses ignoreCase
        CardEntity[] cardsArr = cardRepo.findByCountry(country);
        CardEntity[] cardsArr1 = cardRepo.findByCity(country + " ");
        //empty arrayList as container for answer
        ArrayList<Long> cards = new ArrayList<>();
        ArrayList<Long> cards1 = new ArrayList<>();
        //fill arrayList
        for (CardEntity card : cardsArr)
            cards.add(card.getID());
        for (CardEntity card : cardsArr1)
            cards1.add(card.getID());
        Set<Long> set = new HashSet<>();
        set.addAll(cards);
        set.addAll(cards1);
        //return answer
        return new ArrayList<>(set);
    }

    /**
     * Finds all cards that contain some hashtags.
     *
     * @param hashtags str with all hashtags
     * @return list with card's ids
     */
    public ArrayList<Long> getListByHashtag(String hashtags) {
        //initialize all storages needed for search
        ArrayList<Long> cards = new ArrayList<>();
        ArrayList<String> strs = new ArrayList<>(Arrays.asList(hashtags.split("#")));
        //as String.split() is used check and delete empty lines
        if (strs.get(0).equals("")) strs.remove(0);

        //searching from hashtags
        for (CardEntity card : cardRepo.findAll()) {
            for (String hash : strs) {
                if (card.getHashtag().toLowerCase().contains(hash.toLowerCase())) {
                    cards.add(card.getID());
                    break; //in way if card contains more than one needed hashtag
                }
            }
        }
        //return list with all cards
        return cards;
    }
}
