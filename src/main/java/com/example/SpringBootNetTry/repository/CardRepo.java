package com.example.SpringBootNetTry.repository;

import com.example.SpringBootNetTry.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardRepo extends CrudRepository<CardEntity, Long> {

    /**
     * Find just one card by its unique id in DataBase
     * @param id card id
     * @return card entity
     */
    CardEntity findById(long id);

    /**
     * Find all cards in DataBase that have such City.
     * Request itself uses ignoreCase,
     * so for "london" and "London" there will be same results
     * @param city city name
     * @return card entity array
     */
    CardEntity[] findByCity(String city);

    /**
     * Find all cards in DataBase that have such Country.
     * Request itself uses ignoreCase,
     * so for "russia" and "Russia" there will be same results
     * @param country country name
     * @return card entity array
     */
    CardEntity[] findByCountry(String country);
}
