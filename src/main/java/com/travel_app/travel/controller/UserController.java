package com.travel_app.travel.controller;

import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.entity.UserEntity;
import com.travel_app.travel.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/checkLogin")
    public ResponseEntity<?> checkLogin(@RequestParam String username, @RequestParam String password) {
        UserDto user = userService.findUserByUsername(username);
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


    @PostMapping("/upload-avatar/{userId}")
    public ResponseEntity<String> uploadAvatar(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            // Chuyển đổi file thành dữ liệu base64
            byte[] fileBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(fileBytes);

            // Lưu dữ liệu base64 vào trường avatar của UserEntity
            userService.saveAvatar(userId, base64Image);

            return ResponseEntity.ok("Avatar uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload avatar: " + e.getMessage());
        }
    }



//    @GetMapping("/login/google")
//    public String googleLogin() {
//        return "redirect:/oauth2/authorization/google";
//    }
//
//    @GetMapping("/oauth2/callback")
//    public String loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {
//
//        //Get email from OAuth2User and check if user exists in database
//        String email = oauth2User.getAttribute("email");
//        UserDto user = userService.findUserByEmail(email);
//        if (user == null) {
//            // User does not exist, create new user
//            user = new UserDto();
//            user.setEmail(email);
//            // Set other user properties as needed
//            userService.save(user);
//        }
//        // Redirect user to home page
//        return "redirect:/";
//    }
}
