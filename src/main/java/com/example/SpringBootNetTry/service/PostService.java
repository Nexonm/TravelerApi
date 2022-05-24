package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.PostEntity;
import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.post.PostDoesNotExistsException;
import com.example.SpringBootNetTry.exception.user.UserDoesNotExistException;
import com.example.SpringBootNetTry.mapper.PostEntityMapper;
import com.example.SpringBootNetTry.model.PostModel;
import com.example.SpringBootNetTry.repository.PostRepo;
import com.example.SpringBootNetTry.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    public PostModel makePost(String gsonStr, long userId) throws UserDoesNotExistException {
        PostEntity post = PostEntityMapper.toPostEntityFromJson(gsonStr);
        UserEntity user = userRepo.findById(userId);
        if (user == null) throw new UserDoesNotExistException();
        post.setUser(user);
        postRepo.save(post);

        return PostEntityMapper.toPostModelFromPostEntity(post, false);
    }

    public PostModel getOnePostById(long id) throws PostDoesNotExistsException {
        if (postRepo.findById(id) == null)
            throw new PostDoesNotExistsException();
        return PostEntityMapper.toPostModelFromPostEntity(postRepo.findById(id), false);
    }

    public ArrayList<Long> getListByHashtag(String hashtags) {
        ArrayList<Long> posts = new ArrayList<>();
        Pattern MY_PATTERN = Pattern.compile("#(\\S+)");
        Matcher mat = MY_PATTERN.matcher(hashtags);
        ArrayList<String> strs = new ArrayList<String>();
        while (mat.find()) {
            //System.out.println(mat.group(1));
            strs.add(mat.group(1));
        }
        for(PostEntity post : postRepo.findAll()){
            for (String hash : strs) {
                if(post.getHashtag().contains(hash))
                    posts.add(post.getID());
            }
        }
        return posts;
    }
}
