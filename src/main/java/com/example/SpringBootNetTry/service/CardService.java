package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.card.CardDoesNotExistsException;
import com.example.SpringBootNetTry.exception.user.UserDoesNotExistException;
import com.example.SpringBootNetTry.mapper.CardEntityMapper;
import com.example.SpringBootNetTry.model.CardModel;
import com.example.SpringBootNetTry.repository.CardRepo;
import com.example.SpringBootNetTry.repository.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.smartcardio.Card;
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
        person.setUserCards(card);
        cardRepo.save(card);

        //TODO Сделать проверку на полную идентичность карт

        return CardEntityMapper.toCardModel(cardRepo.save(card), false);
    }

    @Deprecated
    public CardEntity makeCard(CardEntity card) {

        cardRepo.save(card);
        //TODO Сделать проверку на полную идентичность карт

        return cardRepo.save(card);
    }

    public CardModel getOneCardById(long id) throws CardDoesNotExistsException {
        if (cardRepo.findById(id) == null)
            throw new CardDoesNotExistsException();
        return CardEntityMapper.toCardModel(cardRepo.findById(id), true);
    }

    /**
     * Finds all cards by str from server
     * @param str some str
     * @return list with card's ids
     */
    public ArrayList<Long> getListThreeSorted(String str){
        Set<Long> set = new HashSet<>();
        set.addAll(getListByCity(str));
        set.addAll(getListByCountry(str));
        set.addAll(getListByHashtag(str));
        return new ArrayList<>(set);
    }

    /**
     * Finds all cards by city, uses ignoreCase
     * @param city name of city
     * @return list with card's ids
     */
    public ArrayList<Long> getListByCity(String city) {
        //get all cards by city, uses ignoreCase
        CardEntity[] cardsArr = cardRepo.findByCity(city);
        CardEntity[] cardsArr1 = cardRepo.findByCity(city+" ");
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
     * @param country name of country
     * @return list with card's ids
     */
    public ArrayList<Long> getListByCountry(String country) {
        //get all cards by country, uses ignoreCase
        CardEntity[] cardsArr = cardRepo.findByCountry(country);
        CardEntity[] cardsArr1 = cardRepo.findByCity(country+" ");
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
