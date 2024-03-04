package com.travel_app.travel.service;

import com.travel_app.travel.dto.Root;
import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.entity.UserEntity;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface IUserService {
    UserDto save(UserDto userDto);
    UserDto findUserByUsername(String username);
    UserDto findUserByEmail(String email);
    boolean checkPassword(String plainPassword, String hashedPassword);

    UserDto update(UserDto userDto);

    void delete(long id);
    void saveAvatar(long id, String filePath);
    List<UserDto> getAllUser();

    Root toPerson(Map<String, Object> map);
    public boolean isEmailUnique(String email);
}
