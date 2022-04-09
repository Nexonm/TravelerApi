package com.example.SpringBootNetTry.repository;

import com.example.SpringBootNetTry.entity.UserEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageServiceRepo {

    void init();

    String storeUserPhoto(MultipartFile file, long id);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();
}
