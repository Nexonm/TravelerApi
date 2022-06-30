package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.user.*;
import com.example.SpringBootNetTry.service.UserService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/people")
public class UserController {

    private static final String exceptionInController = "Произошла ошибка в PersonController";


    @Autowired
    private UserService pService;

    /**
     * Method calls when user enters mobile app.
     *
     * @param email user email as login
     * @param pass  user password for email
     * @return userModel as JSON str
     */
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
            return ResponseEntity.badRequest().body("error");
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

    //it is not good, we can't just get some info from people by some id
    //TODO delete comment this method for production
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
            return ResponseEntity.badRequest().body(exceptionInController);
        }
    }

    //for admin tests
    //TODO delete comment this method for production
    @GetMapping("/get/all")
    public ResponseEntity getAll(@RequestParam(name = "ap") String password) {
        try {
            return ResponseEntity.ok(pService.getAll(password));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(exceptionInController);
        }
    }

//    @DeleteMapping("/delete")
//    public ResponseEntity deletePersonById(
//            @RequestParam(name = "id") long id,
//            @RequestParam(name = "email") String email,
//            @RequestBody String pass ) {
//        try {
//            if (pService.deletePersonById(id))
//                return ResponseEntity.ok("Пользователь был удалён");
//            else
//                throw new Exception();
//        } catch (UserDoesNotExistException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(exceptionInController);
//        }
//    }

    /**
     * Add card id to user.favoriteCards.
     *
     * @param uid user id
     * @param cid card id
     * @return user.favoriteCards array list as JSON str
     */
    @GetMapping("/add-to-favs")
    public ResponseEntity addCardToUserFavorite(
            @RequestParam(name = "uid") long uid,
            @RequestParam(name = "cid") long cid
    ) {
        try {

            return ResponseEntity.ok(
                    (new Gson()).toJson(pService.addOneCardToFavorite(uid, cid))
            );
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(exceptionInController);
        }
    }

    @PostMapping("/edit-contacts")
    public ResponseEntity editUserContacts(
            @RequestPart (name = "data") String gsonStr,
            @RequestPart (name = "pass") String pass) {
        try {
            return ResponseEntity.ok(
                    (new Gson()).toJson(pService.editUserContacts(gsonStr, pass))
            );
        } catch (UserDoesNotExistException |
                UserIncorrectPasswordException |
                UserDataFormatException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(exceptionInController);
        }
    }
}