package com.example.SpringBootNetTry.repository;

import com.example.SpringBootNetTry.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepo extends CrudRepository<PostEntity, Long> {
    PostEntity findById(long id);
}
