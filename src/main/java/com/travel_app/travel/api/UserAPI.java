package com.travel_app.travel.api;

import com.travel_app.travel.dto.UsernameExistsException;
import com.travel_app.travel.entity.UserEntity;
import com.travel_app.travel.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class UserAPI {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity<String> createUser(@RequestBody UserEntity userEntity) {
        try {
            userService.save(userEntity);
            return ResponseEntity.ok("User registered successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/getUserByUsername")
    public UserEntity findOneUser(@RequestParam String username){
        return userService.findUserByUsername(username);
    }

    @PostMapping("/checkLogin")
    public ResponseEntity<?> checkLogin(@RequestParam String username, @RequestParam String password) {
        UserEntity user = userService.findUserByUsername(username);
        if (user != null) {
            String matkhau = user.getPassword();
            if (userService.checkPassword(password, matkhau)) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    }
}
