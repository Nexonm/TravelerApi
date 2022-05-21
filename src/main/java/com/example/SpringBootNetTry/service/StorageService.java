package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.exception.storage.FileNotFoundException;
import com.example.SpringBootNetTry.exception.storage.StorageException;
import com.example.SpringBootNetTry.repository.CardRepo;
import com.example.SpringBootNetTry.repository.PostRepo;
import com.example.SpringBootNetTry.repository.StorageServiceRepo;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    @Autowired
    private PostRepo postRepo;

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }


    /**
     * Saves Photo to user's directory. This photo is used as user avatar
     *
     * @param file file photo png/jpj
     * @param id   user's id who sent request
     * @return path to photo starts with main storage directory
     * @throws StorageException
     */

    @Override
    public String storeUserPhoto(MultipartFile file, long id) throws StorageException {
        //clean filepath to assign new filepath
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        //checking
//        if (file.isEmpty()) {
//            throw new StorageException("Failed to store empty file " + filename);
//        }
//        if (filename.contains("..")) {
//            // This is a security check
//            throw new StorageException(
//                    "Cannot store file with relative path outside current directory "
//                            + filename);
//        }
        String filename = genName();
        //MAIN JOB
        try {
            //System.out.println("=======Path=======" + rootLocation.toString());
            InputStream inputStream = file.getInputStream();
            //create directory for user
            if (!existsDirForUserAvatar(id)) {
                createDirectoryForUserAvatar(id);
            }
            //copy file to new directory
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

    /**
     * Saves Photo to user's directory. This photo is used for user's cards
     *
     * @param file file photo png/jpj
     * @param id   user's id who sent request
     * @return path to photo starts with main storage directory
     * @throws StorageException
     */

    @Override
    public String storeCardsPhoto(MultipartFile file, long id) throws StorageException {
        //clean filepath to assign new filepath
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        //checking
//        if (file.isEmpty()) {
//            throw new StorageException("Failed to store empty file " + filename);
//        }
//        if (filename.contains("..")) {
//            // This is a security check
//            throw new StorageException(
//                    "Cannot store file with relative path outside current directory "
//                            + filename);
//        }
        //MAIN JOB
        String filename = genName();
        try {
            System.out.println("=======Path_Root=======" + rootLocation.toString());
            InputStream inputStream = file.getInputStream();
            //create directory for user (main dir for user)
            if (!existsDirForUserAvatar(cardRepo.findById(id).getUser().getID())) {
                createDirectoryForUserAvatar(cardRepo.findById(id).getUser().getID());
            }
            //create directory for user cards
            if (!existsDirForUserCards(id)) {
                createDirectoryForUserCards(id);
            }
            //copy file to new directory
            Files.copy(
                    inputStream,
                    Paths.get(getPathToUsersCardsPhoto(id, filename))
            );

            inputStream.close();
            System.out.println("===SETTING_PATH_NAME_TO_CARD");
            setPathToCard(id, filename);
            System.out.println("===SET_PATH_NAME_TO_CARD" + cardRepo.findById(id).getPathToPhoto());
        } catch (IOException e) {
            return "Ошибка";
        }


        return filename;
    }

    /**
     * Saves Photo to user's directory. This photo is used for user's cards
     *
     * @param file file photo png/jpj
     * @param id   user's id who sent request
     * @return path to photo starts with main storage directory
     * @throws StorageException
     */

    @Override
    public String storePostPhoto(MultipartFile file, long id) throws StorageException {
        //clean filepath to assign new filepath
//        String filename = StringUtils.cleanPath(file.getOriginalFilename());
//        //checking
//        if (file.isEmpty()) {
//            throw new StorageException("Failed to store empty file " + filename);
//        }
//        if (filename.contains("..")) {
//            // This is a security check
//            throw new StorageException(
//                    "Cannot store file with relative path outside current directory "
//                            + filename);
//        }
        //MAIN JOB
        String filename = genName();
        try {
            System.out.println("=======Path_Root=======" + rootLocation.toString());
            InputStream inputStream = file.getInputStream();
            //create directory for user (main dir for user)
            if (!existsDirForUserAvatar(postRepo.findById(id).getUser().getID())) {
                createDirectoryForUserAvatar(postRepo.findById(id).getUser().getID());
            }
            //create directory for user cards
            if (!existsDirForUserPosts(id)) {
                createDirectoryForUserPosts(id);
            }
            //copy file to new directory
            Files.copy(
                    inputStream,
                    Paths.get(getPathToUsersPostsPhoto(id, filename))
            );

            inputStream.close();
            System.out.println("===SETTING_PATH_NAME_TO_POST");
            setPathToCard(id, filename);
            System.out.println("===SET_PATH_NAME_TO_POST" + postRepo.findById(id).getPathToPhoto());
        } catch (IOException e) {
            return "Ошибка";
        }


        return filename;
    }

    /**
     * Sets path to card and saves changes in DB.
     *
     * @param id       user id
     * @param fileName file name
     */
    private void setPathToCard(long id, String fileName) {
        //sets path
        cardRepo.findById(id).setPathToPhoto(getPathToUsersCardsPhoto(id, fileName));
        //saves user with changes to DB
        cardRepo.save(cardRepo.findById(id));
    }

    /**
     * Sets path to post and saves changes in DB.
     *
     * @param id       user id
     * @param fileName file name
     */
    private void setPathToPost(long id, String fileName) {
        //sets path
        postRepo.findById(id).setPathToPhoto(getPathToUsersCardsPhoto(id, fileName));
        //saves user with changes to DB
        postRepo.save(postRepo.findById(id));
    }

    /**
     * Sets path to user and saves changes in DB.
     *
     * @param id       user id
     * @param fileName file name
     */
    private void setPathToUser(long id, String fileName) {
        //sets path
        userRepo.findById(id).setPathToPhoto(getPathToUserPhoto(id, fileName));
        //saves user with changes to DB
        userRepo.save(userRepo.findById(id));
    }

    /**
     * Сhecks if there is directory for user's card's photo
     *
     * @param id user id to get it's email
     * @return true if dir exists
     */
    private boolean existsDirForUserCards(long id) {
        return new File(rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\cards").exists() &&
                new File(rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\cards").isDirectory();
    }

    /**
     * Сhecks if there is directory for user
     *
     * @param id user id to get it's email
     * @return true if dir exists
     */
    private boolean existsDirForUserAvatar(long id) {
        return new File(rootLocation + "\\" + userRepo.findById(id).getEmail()).exists() &&
                new File(rootLocation + "\\" + userRepo.findById(id).getEmail()).isDirectory();
    }

    /**
     * Сhecks if there is directory for user's post's photo
     *
     * @param id user id to get it's email
     * @return true if dir exists
     */
    private boolean existsDirForUserPosts(long id) {
        return new File(rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\posts").exists() &&
                new File(rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\posts").isDirectory();
    }

    /**
     * Creates directory to store user's post's photo
     * in case there is no directory for user posts
     *
     * @param id user id to get it's email
     * @return Path to dir
     * @throws IOException
     */
    private Path createDirectoryForUserCards(long id) throws IOException {
        //creates directory for cards
        return Files.createDirectory(Paths.get(rootLocation.toString() + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\cards"));
    }

    /**
     * Creates directory to store user's cards photo
     * in case there is no directory for user cards
     *
     * @param id user id to get it's email
     * @return Path to dir
     * @throws IOException
     */
    private Path createDirectoryForUserPosts(long id) throws IOException {
        //creates directory for cards
        return Files.createDirectory(Paths.get(rootLocation.toString() + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\posts"));
    }

    /**
     * Creates directory to store user's avatar photo
     * in case there is no directory for user
     *
     * @param id user id to get it's email
     * @return Path to dir
     * @throws IOException
     */
    private Path createDirectoryForUserAvatar(long id) throws IOException {
        //creates directory
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

    /**
     * Gets card photo when it's necessary
     *
     * @param id user's id
     * @return card's photo as File jpg/png
     */
    public File getCardPhoto(long id) {
        System.out.println("===PATH_NAME_TO_USER" + cardRepo.findById(id).getPathToPhoto());
        File file = new File(cardRepo.findById(id).getPathToPhoto());
        if (file.isDirectory()) {
            return null;
        }
        System.out.println("===file.size" + file.getName());
        return file;
    }

    /**
     * Gets post photo when it's necessary
     *
     * @param id user's id
     * @return post's photo as File jpg/png
     */
    public File getPostPhoto(long id) {
        System.out.println("===PATH_NAME_TO_USER" + postRepo.findById(id).getPathToPhoto());
        File file = new File(postRepo.findById(id).getPathToPhoto());
        if (file.isDirectory()) {
            return null;
        }
        System.out.println("===file.size" + file.getName());
        return file;
    }

    /**
     * Gets user photo when it's necessary
     *
     * @param id user's id
     * @return user's avatar photo as File jpg/png
     */
    public File getUserPhoto(long id) {
        System.out.println("===PATH_NAME_TO_USER" + userRepo.findById(id).getPathToPhoto());
        File file = new File(userRepo.findById(id).getPathToPhoto());
        if (file.isDirectory()) {
            return null;
        }
        System.out.println("===file.size" + file.getName());
        return file;
    }

    /**
     * Gets path to card's photo from DB
     *
     * @param id user's id
     * @return path to photo
     */
    public String getCardPhotoTwo(long id) {
        File file = new File(cardRepo.findById(id).getPathToPhoto());
        String str = file.getAbsolutePath();
        return str;
    }

    /**
     * Gets path to post's photo from DB
     *
     * @param id user's id
     * @return path to photo
     */
    public String getPostPhotoTwo(long id) {
        File file = new File(postRepo.findById(id).getPathToPhoto());
        String str = file.getAbsolutePath();
        return str;
    }

    /**
     * Gets path to user's avatar from DB
     *
     * @param id user's id
     * @return path to photo
     */
    public String getUserPhotoTwo(long id) {
        return userRepo.findById(id).getPathToPhoto();
    }

    /**
     * method generate new name for file in order to store in correctly
     * @return file name from random 10 chars
     */
    private String genName() {
        int range = ('z' - 'a') + 1;
        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < 10; i++) {
            builder.append((char)(((int)(Math.random() * range)) + 'a'));
        }
        return builder.toString();
    }

    /**
     * Combines all in one path to user photo
     *
     * @param id       user's id
     * @param fileName file name
     * @return
     */
    private String getPathToUserPhoto(long id, String fileName) {
        return (rootLocation + "\\" + userRepo.findById(id).getEmail() + "\\" + fileName);
    }

    /**
     * Combines all in one path to user's card's photo
     *
     * @param id       user's id
     * @param fileName file name
     * @return
     */
    private String getPathToUsersCardsPhoto(long id, String fileName) {
        return (rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\cards\\" + fileName);
    }

    /**
     * Combines all in one path to user's card's photo
     *
     * @param id       user's id
     * @param fileName file name
     * @return
     */
    private String getPathToUsersPostsPhoto(long id, String fileName) {
        return (rootLocation + "\\" + cardRepo.findById(id).getUser().getEmail() + "\\posts\\" + fileName);
    }

}
