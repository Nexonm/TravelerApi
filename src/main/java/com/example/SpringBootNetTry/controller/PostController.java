package com.example.SpringBootNetTry.controller;

import com.example.SpringBootNetTry.exception.post.PostDoesNotExistsException;
import com.example.SpringBootNetTry.exception.user.UserDoesNotExistException;
import com.example.SpringBootNetTry.service.PostService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService pService;

    @PostMapping("/add/gson")
    public ResponseEntity makePost(
            @RequestBody String gsonStr,
            @RequestParam(name = "uid") long uid
    ) {
        try {
            System.out.println("Someone trying to POST post user id=" + uid
                    + "\ngson String:" + gsonStr);
            String gson = (new Gson()).toJson(pService.makePost(gsonStr, uid));
            return ResponseEntity.ok(gson);
        } catch (UserDoesNotExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/get")
    public ResponseEntity getOnePostById(@RequestParam("id") long id) {
        try {
            System.out.println("someone trying to GET post");

            String gson = (new Gson()).toJson(pService.getOnePostById(id));
            return ResponseEntity.ok(gson);
        }catch (PostDoesNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("get-by-hashtags")
    public ResponseEntity getPostsByHashtags(@RequestParam("hashtags") String hashtags){
        try{
            String gson = (new Gson()).toJson(pService.getListByHashtag(hashtags));
            return ResponseEntity.ok(gson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

}
