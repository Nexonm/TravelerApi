package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.user.UserAlreadyExistsException;
import com.example.SpringBootNetTry.exception.user.UserDoeNottExistsException;
import com.example.SpringBootNetTry.model.UserModel;
import com.example.SpringBootNetTry.repository.UserRepo;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StorageService storageService;

    /** Registration method. It is first to register new user.
     * Saves only 5 main fields, as:
     *  String: email
     *  String: password (hash)
     *  String: firstName
     *  String: secondName
     *  long: dateOfBirth (on mobile app required check if user isn't under 18.
     * @param gsonStr JSON format str
     * @return
     * @throws UserAlreadyExistsException
     */
    public UserEntity registrationMain(String gsonStr) throws UserAlreadyExistsException {
        UserEntity user = (new Gson()).fromJson(gsonStr, UserEntity.class);
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        try {
            //usually it works
            String hex = String.format("%064x", new BigInteger(1,
                    MessageDigest.getInstance("SHA3-256").digest(
                            user.getPassword().getBytes(StandardCharsets.UTF_8)
                    )));
            user.setPassword(hex);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        user.setUserFavoriteCards(null);
        user.setUserCards(null);
        return userRepo.save(user);
    }

    /** Registration additional method. It is second to register new user.
     *  Sends new fields such as:
     *  String: phoneNumber
     *  String: socialContacts
     *  boolean: isMale
     * @param gsonStr JSON format str
     * @return
     * @throws UserAlreadyExistsException
     */
    public UserEntity registrationAdd(String gsonStr) throws UserAlreadyExistsException {
        UserEntity user = (new Gson()).fromJson(gsonStr, UserEntity.class);
        UserEntity userMain = userRepo.findByEmail(user.getEmail());
        userMain.combine(user);
        return userRepo.save(user);
    }

    public UserModel getOnePersonById(long id) throws UserDoeNottExistsException {
        if (userRepo.findById(id) == null)
            throw new UserDoeNottExistsException();

        return UserModel.toUserModel(userRepo.findById(id), true);
    }

    public String getUserPath(long id) throws UserDoeNottExistsException {
        if (userRepo.findById(id) == null)
            throw new UserDoeNottExistsException();

        return userRepo.findById(id).getPathToPhoto();
    }

    public ArrayList<UserModel> getAll(String adminPassword) {
        ArrayList<UserModel> models = new ArrayList<>();
        if ("pass154".equals(adminPassword)){
            for (UserEntity entity : userRepo.findAll()) {
                models.add(UserModel.toUserModel(entity, true));
            }
        }
        return models;
    }

    public boolean deletePersonById(long id) throws UserDoeNottExistsException {
        if (userRepo.findById(id) == null)
            throw new UserDoeNottExistsException();

        userRepo.deleteById(id);
        return true;

    }

}