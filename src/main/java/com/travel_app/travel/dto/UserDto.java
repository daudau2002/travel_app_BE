package com.travel_app.travel.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String dob;
    private String gender;
    private String address;
    private String email;
    private String googleId;
    private String avatar;
}
