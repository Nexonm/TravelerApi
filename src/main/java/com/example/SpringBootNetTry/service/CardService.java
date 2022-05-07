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

//аннотация говорит, что этот класс ответственен за всю логику
@Service
public class CardService {

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private UserRepo userRepo;

    public CardModel makeCard( String gsonStr, long personId) throws UserDoesNotExistException{
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
    public CardEntity makeCard( CardEntity card) {

        cardRepo.save(card);
        //TODO Сделать проверку на полную идентичность карт

        return cardRepo.save(card);
    }

    public CardModel getOneCardById(long id) throws CardDoesNotExistsException {
        if (cardRepo.findById(id) == null)
            throw new CardDoesNotExistsException();
        return CardEntityMapper.toCardModel(cardRepo.findById(id), true);
    }
}
