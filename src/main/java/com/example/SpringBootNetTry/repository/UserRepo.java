package com.example.SpringBootNetTry.repository;

import com.example.SpringBootNetTry.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findById(long id);
    boolean existsByEmail(String email);
}
