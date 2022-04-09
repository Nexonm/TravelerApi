package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.entity.CardEntity;
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
    //получается что при запросе мы пишем http://localhost:8080/cards/
    //что в итоге? - сначала адрес сервера, потом @RequestMapping всего контроллера (тут у нас "/cards")
    //затем @GetMapping для определённого метода

    @Autowired
    private CardService cardService;

    @PostMapping("/add/gson")
    public ResponseEntity makeCard(
            @RequestBody String gsonStr,
            @RequestParam(name = "id") long id
    ) {
        try {

            cardService.makeCard(gsonStr, id);
            return ResponseEntity.ok("Карта была сохранена");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PostMapping("/add/gson/file")
    public ResponseEntity makeCard(
            @RequestParam(name = "name") String name,
            @RequestParam("file") MultipartFile file
    ) {
        System.out.println("Method was invoke");
        try {
            if (!file.isEmpty()){
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(new File(name+"-uploaded"))
                );
                stream.write(file.getBytes());
                stream.close();
            }
            return ResponseEntity.ok("Карта была сохранена и файл" + name + "был загружен в ");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка: " + e.getMessage());
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
    @GetMapping("/get")
    public ResponseEntity geOneCardById(@RequestParam("id") long id) {
        try {
            return ResponseEntity.ok(cardService.getOneCardById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
