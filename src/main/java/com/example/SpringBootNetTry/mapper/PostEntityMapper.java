package com.example.SpringBootNetTry.mapper;

import com.example.SpringBootNetTry.entity.PostEntity;
import com.example.SpringBootNetTry.model.PostModel;
import com.google.gson.Gson;

public class PostEntityMapper {

    public static PostModel toPostModelFromPostEntity(PostEntity post, boolean withUserCards) {
        return new PostModel(
                post.getID() + 0,
                (withUserCards ? UserEntityMapper.toUserModel(post.getUser(), false) : null),
                post.getDescription() + "",
                post.getPathToPhoto() + "",
                post.getHashtag() +" "
        );
    }

    public static PostEntity toPostEntityFromJson(String gsonStr){
        return (new Gson()).fromJson(gsonStr, PostEntity.class);
    }
}
