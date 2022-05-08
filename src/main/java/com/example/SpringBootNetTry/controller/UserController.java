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
        try{
            return ResponseEntity.ok((new Gson()).toJson(pService.login(email, pass)));
        }catch (UserDoesNotExistException | UserIncorrectPasswordException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e){
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
            pService.registrationMain(gsonStr.toString());
            return ResponseEntity.ok("Человек был сохранён");

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
        System.out.println("some on trying to get info" + id);
        try {
            for (int i = 1; i < 21; i++) {
                pService.deletePersonById(i);
            }
//            return ResponseEntity.ok(pService.getOnePersonById(id));
//            String str = (new Gson()).toJson(pService.getOnePersonById(id),UserModel.class);
//            System.out.println((new Gson()).toJson(pService.getOnePersonById(id),UserModel.class));
//            return ResponseEntity.ok(pService.getOnePersonById(id));
            return ResponseEntity.ok((new Gson()).toJson(pService.getOnePersonById(id)));
        } catch (UserDoesNotExistException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Произошла ошибка в PersonController");
        }
    }

    @GetMapping("/get-path")
    public ResponseEntity getUserPath(@RequestParam(name = "id") long id) {
        System.out.println("some on trying to get info");
        try {
            return ResponseEntity.ok(pService.getUserPath(id));
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
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

}