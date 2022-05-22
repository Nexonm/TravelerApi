package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.user.*;
import com.example.SpringBootNetTry.mapper.UserEntityMapper;
import com.example.SpringBootNetTry.model.CardModel;
import com.example.SpringBootNetTry.model.UserModel;
import com.example.SpringBootNetTry.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/people")
public class UserController {


    @Autowired
    private UserService pService;

    @GetMapping("/login")
    public ResponseEntity loginUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "pass") String pass
    ) {
        try {
            return ResponseEntity.ok((new Gson()).toJson(pService.login(email, pass)));
        } catch (UserDoesNotExistException | UserIncorrectPasswordException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @Deprecated
    @PostMapping("/add/gson")
    public ResponseEntity makeUser(@RequestBody String gsonStr) {
        try {
            UserEntity user = (new Gson()).fromJson(gsonStr, UserEntity.class);
            System.out.println("Gson cardEntity String: " + gsonStr);
            System.out.println(user.toString());
            //personRepo.save(person);
            return ResponseEntity.ok("Человек был сохранён");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    /**
     * Main registration method. It is used to send first data to server
     *
     * @param gsonStr str with 5 main fields
     * @return
     */
    @PostMapping("/reg-main")
    public ResponseEntity registrationMain(@RequestBody String gsonStr) {
        try {
            System.out.println("Reg new User: " + gsonStr.toString());
            return ResponseEntity.ok((new Gson()).toJson(pService.registrationMain(gsonStr.toString())));

        } catch (UserAlreadyExistsException |
                UserDataNoDateOfBirthException |
                UserDataNoSecondNameException |
                UserDataNoFirstNameException |
                UserDataNoEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    /**
     * Main registration method. It is used to send second additional data to server
     *
     * @param gsonStr str with 3 extra fields
     * @return
     */
    @PostMapping("/reg-add")
    public ResponseEntity registrationAdd(@RequestBody String gsonStr) {
        try {
//            pService.registrationAdd(gsonStr);
            pService.registrationAdd(gsonStr);
            return ResponseEntity.ok("Пользователь был сохранён");

        } catch (UserAlreadyExistsException |
                UserDataNoEmailException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/get")
    public ResponseEntity getOnePersonById(@RequestParam(name = "id") long id) {
//        System.out.println("some on trying to get info" + id);
        try {
            return ResponseEntity.ok((new Gson()).toJson(pService.getOnePersonById(id)));
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка в PersonController");
        }
    }

    //for admin tests
    @GetMapping("/get/all")
    public ResponseEntity getAll(@RequestParam(name = "ap") String password) {
        try {
            return ResponseEntity.ok(pService.getAll(password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка в PersonController");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity deletePersonById(@RequestParam(name = "id") long id) {
        try {
            if (pService.deletePersonById(id))
                return ResponseEntity.ok("Пользователь был удалён");
            else
                throw new Exception();
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка в PersonController");
        }
    }

    /**
     * Add card id to user.favoriteCards.
     * @param uid user id
     * @param cid card id
     * @return user.favoriteCards array list as JSON str
     */
    @GetMapping("/add-to-favs")
    public ResponseEntity addCardToUserFavorite(
            @RequestParam(name = "uid") long uid,
            @RequestParam(name = "cid") long cid
    ) {
        try{

            return ResponseEntity.ok(
                    (new Gson()).toJson(pService.addOneCardToFavorite(uid, cid))
            );
        }catch (UserDoesNotExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка в PersonController");
        }
    }
}