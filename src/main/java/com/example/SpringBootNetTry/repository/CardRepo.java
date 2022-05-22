package com.example.SpringBootNetTry.repository;

import com.example.SpringBootNetTry.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<CardEntity, Long> {
    CardEntity findById(long id);

    CardEntity[] findByCity(String city);
}
