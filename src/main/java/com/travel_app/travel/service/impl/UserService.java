package com.travel_app.travel.service.impl;

import com.travel_app.travel.converter.UserConverter;
import com.travel_app.travel.dto.Root;
import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.dto.UsernameExistsException;
import com.travel_app.travel.repository.UserRepository;
import com.travel_app.travel.service.IUserService;
import jakarta.persistence.Access;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import com.travel_app.travel.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserConverter userConverter;
    private final int BCRYPT_WORKLOAD = 12;
    @Override
    public boolean isEmailUnique(String email) {
        return !userRepository.existsByEmail(email);
    }


    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_WORKLOAD));
    }


    @Override
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    @Override
    public UserDto update(UserDto userDto) {
        UserEntity olduserEntity = userRepository.findOneById(userDto.getId());
        UserEntity userEntity = userConverter.toEntity(userDto, olduserEntity);
        userEntity = userRepository.save(userEntity);
        return userConverter.toDto(userEntity);
    }

    @Override
    public void delete(long id) {
        UserEntity userEntity = userRepository.findOneById(id);
        userRepository.delete(userEntity);
    }

    @Override
    public void saveAvatar(long id, String filePath) {
        UserEntity userEntity = userRepository.findOneById(id);
        userEntity.setAvatar(filePath);
        userEntity = userRepository.save(userEntity);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDto> userDtoList = userConverter.toDtoList(userEntityList);
        return userDtoList;
    }

    @Override
    public Root toPerson(Map<String, Object> map) {
        Root root = new Root();
        root.setEmail((String) map.get("email"));
        root.setPicture((String) map.get("picture"));
        root.setName((String) map.get("name"));
        return root;
    }

    @Override
    public UserDto save(UserDto userDto) {
        if (isEmailUnique(userDto.getEmail())) {
            userDto.setPassword(hashPassword(userDto.getPassword()));
            UserEntity newuser = userConverter.toEntity(userDto);
            newuser = userRepository.save(newuser);
            return userConverter.toDto(newuser);
        } else {
            throw new UsernameExistsException("Username is already taken");
        }
    }

    @Override
    public UserDto findUserByUsername(String username) {
        UserEntity userEntity = userRepository.findOneByUsername(username);
        return userConverter.toDto(userEntity);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        UserEntity userEntity = userRepository.findOneByEmail(email);
        return userConverter.toDto(userEntity);
    }


}
