package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.storage.FileNotFoundException;
import com.example.SpringBootNetTry.exception.storage.StorageException;
import com.example.SpringBootNetTry.repository.CardRepo;
import com.example.SpringBootNetTry.repository.StorageServiceRepo;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import com.example.SpringBootNetTry.repository.UserRepo;
import com.example.SpringBootNetTry.storage.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService implements StorageServiceRepo {

    //root path for directory to save data
    private final Path rootLocation;

    //automatic assignment
    @Autowired
    public StorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Autowired
    private CardRepo cardRepo;
    @Autowired
    private UserRepo userRepo;

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String storeUserPhoto(MultipartFile file, long id) throws StorageException {
        //clean filepath to assign new filepath
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //checking
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file " + filename);
        }
        if (filename.contains("..")) {
            // This is a security check
            throw new StorageException(
                    "Cannot store file with relative path outside current directory "
                            + filename);
        }
        //MAIN JOB
        try {
            //System.out.println("=======Path=======" + rootLocation.toString());
            InputStream inputStream = file.getInputStream();
            //create directory for user
            if (!existsDirForUser(id)) {
                createDirectoryForUser(id);
            }

            Files.copy(
                    inputStream,
                    Paths.get(getPathToUserPhoto(id, filename))
            );

            inputStream.close();
            System.out.println("===SETTING_PATH_NAME_TO_USER");
            setPathToUser(id, filename);
            System.out.println("===SET_PATH_NAME_TO_USER" + userRepo.findById(id).getPathToPhoto());
        } catch (IOException e) {
            return "Ошибка";
        }


        return filename;
    }

    /** Sets path to user and saves changes in DB.
     *
     * @param id user id
     * @param fileName file name
     */
    private void setPathToUser(long id, String fileName) {
        userRepo.findById(id).setPathToPhoto(getPathToUserPhoto(id, fileName));
        userRepo.save(userRepo.findById(id));
    }

    /**
     * checks if there is directory for user
     *
     * @param id user id to get it's email
     * @return true if dir exists
     */
    private boolean existsDirForUser(long id) {
        return new File(rootLocation + "\\" + userRepo.findById(id).getEmail()).exists() &&
                new File(rootLocation + "\\" + userRepo.findById(id).getEmail()).isDirectory();
    }

    /**
     * Creates directory in case there is no directory for user
     *
     * @param id user id to get it's email
     * @return Path to dir
     * @throws IOException
     */
    private Path createDirectoryForUser(long id) throws IOException {
        return Files.createDirectory(Paths.get(rootLocation.toString() + "\\" + userRepo.findById(id).getEmail()));
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public File getUserPhoto(long id) {
        System.out.println("===PATH_NAME_TO_USER" + userRepo.findById(id).getPathToPhoto());
        File file = new File(userRepo.findById(id).getPathToPhoto());
        if (file.isDirectory()) {
            return null;
        }
        System.out.println("===file.size" + file.getName());
        return file;
    }

    public String getUserPhotoTwo(long id) {
        return userRepo.findById(id).getPathToPhoto();
    }

    private String getPathToUserPhoto(long id, String fileName) {
        return (rootLocation + "\\" + userRepo.findById(id).getEmail() + "\\" + fileName);
    }


}
