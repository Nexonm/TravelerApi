package com.example.SpringBootNetTry.service;

import com.example.SpringBootNetTry.entity.UserEntity;
import com.example.SpringBootNetTry.exception.user.*;
import com.example.SpringBootNetTry.mapper.UserEntityMapper;
import com.example.SpringBootNetTry.model.UserModel;
import com.example.SpringBootNetTry.repository.UserRepo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private StorageService storageService;

    /**
     * Tries to log user in. In case user is logged in, sends all data about user.
     * In case there in no user or his data is wrong exception is thrown.
     * @param email user account email
     * @param pass user account password
     * @return user model representation
     * @throws UserDoesNotExistException
     * @throws UserIncorrectPasswordException
     */
    public UserModel login(String email, String pass)
            throws UserDoesNotExistException,
            UserIncorrectPasswordException{
        if (!userRepo.existsByEmail(email))
            throw new UserDoesNotExistException();
        UserEntity user = userRepo.findByEmail(email);
        try{
            String hex = String.format("%064x", new BigInteger(1,
                    MessageDigest.getInstance("SHA3-256").digest(
                            pass.getBytes(StandardCharsets.UTF_8)
                    )));
            if (user.getPassword().equals(hex)) return UserEntityMapper.toUserModel(user, true);
            else throw new UserIncorrectPasswordException();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Registration method. It is first to register new user.
     * Saves only 5 main fields, as:
     * <ul>
     *     <li>String: email</li>
     *     <li>String: password</li>
     *     <li>String: firstName</li>
     *     <li>String: secondName</li>
     *     <li>long: dateOfBirth (required check if user isn't under 18)</li>
     * </ul>
     * @param gsonStr JSON format str
     * @return
     * @throws UserAlreadyExistsException
     * @throws UserDataNoDateOfBirthException
     * @throws UserDataNoFirstNameException
     * @throws UserDataNoSecondNameException
     * @throws UserDataNoEmailException
     * @throws UserDataFormatException
     */
    public UserModel registrationMain(String gsonStr)
            throws UserAlreadyExistsException,
            UserDataNoDateOfBirthException,
            UserDataNoFirstNameException,
            UserDataNoSecondNameException,
            UserDataNoEmailException,
            UserDataFormatException{
        UserEntity user = UserEntityMapper.toUserEntity(gsonStr);
        if (user == null) throw new UserDataFormatException();

        if (user.getEmail() == null)
            throw new UserDataNoEmailException();
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException();
        }

        try {
            //usually it works, make hash of password
            String hex = String.format("%064x", new BigInteger(1,
                    MessageDigest.getInstance("SHA3-256").digest(
                            user.getPassword().getBytes(StandardCharsets.UTF_8)
                    )));
            user.setPassword(hex);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        user.setUserFavoriteCards(new ArrayList<>());
        user.setUserCards(null);//method itself creates new arraylist
        check(user);
        long id = userRepo.save(user).getID();
        return UserEntityMapper.toUserModel(userRepo.findById(id), true);
    }

    private void check(UserEntity entity)
            throws UserDataNoDateOfBirthException,
            UserDataNoFirstNameException,
            UserDataNoSecondNameException {
        if (entity.getDateOfBirth() == null)
            throw new UserDataNoDateOfBirthException();
        if (entity.getFirstName() == null)
            throw new UserDataNoFirstNameException();
        if (entity.getSecondName() == null)
            throw new UserDataNoSecondNameException();
    }

    /**
     * Registration additional method. It is second to register new user.
     * Sends new fields such as:
     * <ul>
     *     <li>String: phoneNumber</li>
     *     <li>String: socialContacts</li>
     *     <li>boolean: isMale</li>
     * </ul>
     *
     * @param gsonStr JSON format str
     * @return
     * @throws UserAlreadyExistsException
     * @throws UserDataNoEmailException
     * @throws UserDataFormatException
     */
    public UserEntity registrationAdd(String gsonStr)
            throws UserAlreadyExistsException,
            UserDataNoEmailException,
            UserDataFormatException{
        UserEntity user = (new Gson()).fromJson(gsonStr, UserEntity.class);
        if (user == null) throw new UserDataFormatException();
        UserEntity userMain = userRepo.findByEmail(user.getEmail());
        if (userMain == null)
            throw new UserDataNoEmailException();
        userMain.combine(user);
        return userRepo.save(userMain);
    }

    public UserModel getOnePersonById(long id) throws UserDoesNotExistException {
        if (userRepo.findById(id) == null)
            throw new UserDoesNotExistException();

        return UserEntityMapper.toUserModel(userRepo.findById(id), true);
    }

    public ArrayList<UserModel> getAll(String adminPassword) {
        ArrayList<UserModel> models = new ArrayList<>();
        if ("pass154".equals(adminPassword)) {
            for (UserEntity entity : userRepo.findAll()) {
                models.add(UserEntityMapper.toUserModel(entity, true));
            }
        }
        return models;
    }

    public boolean deletePersonById(long id) throws UserDoesNotExistException {
        if (userRepo.findById(id) == null)
            throw new UserDoesNotExistException();

        userRepo.deleteById(id);
        return true;

    }

    /**
     * Add card id to user.favoriteCards.
     * @param uid user id
     * @param cid card id
     * @return array list from user
     * @throws UserDoesNotExistException
     */
    public ArrayList<Long> addOneCardToFavorite(long uid, long cid) throws UserDoesNotExistException{
        if (userRepo.findById(uid) == null)
            throw new UserDoesNotExistException();
        UserEntity entity = userRepo.findById(uid);
        entity.getUserFavoriteCards().add(cid);
        userRepo.save(entity);
        return entity.getUserFavoriteCards();
    }

}
