package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.entity.CardEntity;
import com.example.SpringBootNetTry.exception.user.UserDoesNotExistException;
import com.example.SpringBootNetTry.service.CardService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

//аннотация нужна чтобы спринг понимал что это класс - контроллер
//этот класс ответственен за логику запросов про карты
@RestController
@RequestMapping("/cards") //показываем куда идет запрос и почему
//так же @RequestMapping говорит, с чего начинается запрос в URL
public class CardController {
    //получается что при запросе мы пишем http://localhost:8080/cards/ (только для тестов на локальном устройстве)
    //что в итоге? - сначала адрес сервера, потом @RequestMapping всего контроллера (тут у нас "/cards")
    //затем @GetMapping для определённого метода

    @Autowired
    private CardService cardService;

    /**
     * Main method to create new card. Data sends as a JSON string.
     * For understanding which user created card we need user ID (uid)
     * @param gsonStr card in JSON format
     * @param id user ID
     * @return json card str
     */
    @PostMapping("/add/gson")
    public ResponseEntity makeCard(
            @RequestBody String gsonStr,
            @RequestParam(name = "uid") long id
    ) {
        try {

            System.out.println("someone trying to POST card user id=" + id
                    + "\ngson String:" + gsonStr);
            String gson = (new Gson()).toJson(cardService.makeCard(gsonStr, id));
            System.out.println("i return : " + gson);
            return ResponseEntity.ok(gson);
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @Deprecated
    @PostMapping("/add")
    public ResponseEntity makeCard(@RequestBody CardEntity card) {
        try {

            cardService.makeCard(card);
            return ResponseEntity.ok("Карта была сохранена");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }


    //с помощью аннотации указываем тип запроса и добавляем значение для URL

    /**
     * Main GET method. Needs card id to return card
     * @param id card id
     * @return JSON card str
     */
    @GetMapping("/get")
    public ResponseEntity getOneCardById(@RequestParam("id") long id) {
        try {
            System.out.println("someone trying to GET card");
            //model is returned
            String gson = (new Gson()).toJson(cardService.getOneCardById(id));
            System.out.println(gson);
            return ResponseEntity.ok(gson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    /**
     * Method returns data sorting by hashtags. If there is matching in card,
     * it will be returned.
     * f.e. for string "#Italy#sport" will be returned all cards that have such hashtags.
     * @param hashtags string containing hashtags
     * @return array list with ids of cards
     */
    @GetMapping("/get-by-hashtags")
    public ResponseEntity getCardsByHashtags(@RequestParam("hashtags") String hashtags) {
        try {
            //model is returned
            String gson = (new Gson()).toJson(cardService.getListByHashtag(hashtags));
            System.out.println(gson);
            return ResponseEntity.ok(gson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    /**
     * Method returns data sorting by city. If there is matching in card,
     * it will be returned.
     * f.e. for string "London" will be returned all cards that have such city.
     * @param city string containing city
     * @return array list with ids of cards
     */
    @GetMapping("/get-by-city")
    public ResponseEntity getCardsByCity(@RequestParam("city") String city) {
        try {
            //model is returned
            String gson = (new Gson()).toJson(cardService.getListByCity(city));
            System.out.println(gson);
            return ResponseEntity.ok(gson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
