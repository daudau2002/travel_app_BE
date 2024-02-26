package com.travel_app.travel.service;

import com.travel_app.travel.entity.UserEntity;

public interface IUserService {
    UserEntity save(UserEntity userEntity);
    UserEntity findUserByUsername(String username);
    boolean checkPassword(String plainPassword, String hashedPassword);
}
