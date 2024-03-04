package com.travel_app.travel.converter;

import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    public UserEntity toEntity (UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setDob(dto.getDob());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setGoogleId(dto.getGoogleId());
        entity.setAvatar(dto.getAvatar());
        return entity;
    }

    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setDob(entity.getDob());
        dto.setGender(entity.getGender());
        dto.setAddress(entity.getAddress());
        dto.setEmail(entity.getEmail());
        dto.setGoogleId(entity.getGoogleId());
        dto.setAvatar(entity.getAvatar());
        return dto;
    }

    public UserEntity toEntity (UserDto dto, UserEntity entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setDob(dto.getDob());
        entity.setGender(dto.getGender());
        entity.setAddress(dto.getAddress());
        entity.setEmail(dto.getEmail());
        entity.setGoogleId(dto.getGoogleId());
        entity.setAvatar(dto.getAvatar());
        return entity;
    }

    public List<UserDto> toDtoList(List<UserEntity> userEntityList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDtoList.add(toDto(userEntity));
        }
        return userDtoList;
    }
}
