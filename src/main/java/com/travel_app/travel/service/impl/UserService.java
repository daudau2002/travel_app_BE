package com.travel_app.travel.service.impl;

import com.travel_app.travel.dto.UsernameExistsException;
import com.travel_app.travel.repository.UserRepository;
import com.travel_app.travel.service.IUserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import com.travel_app.travel.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    private final int BCRYPT_WORKLOAD = 12;

    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_WORKLOAD));
    }


    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        if (isUsernameUnique(userEntity.getUsername())) {
            UserEntity newuser = new UserEntity();
            newuser.setPassword(hashPassword(userEntity.getPassword()));
            newuser.setId(userEntity.getId());
            newuser.setName(userEntity.getName());
            newuser.setUsername(userEntity.getUsername());
            newuser.setDob(userEntity.getDob());
            newuser.setGender(userEntity.getGender());
            UserEntity user = userRepository.save(newuser);
            return user;
        } else {
            throw new UsernameExistsException("Username is already taken");
        }
    }

    @Override
    public UserEntity findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        return userEntity;
    }


}
