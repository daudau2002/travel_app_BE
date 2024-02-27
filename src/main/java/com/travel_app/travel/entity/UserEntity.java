package com.travel_app.travel.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "user")
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String dob;
    @Column
    private String gender;
    @Column
    private String address;
    @Column (nullable = false)
    private String email;
    @Column
    private String googleId;
    @Column(columnDefinition = "LONGTEXT")
    private String avatar;
    @Column(name = "role")
    private int role;
}
