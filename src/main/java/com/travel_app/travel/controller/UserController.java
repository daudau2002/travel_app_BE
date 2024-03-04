package com.travel_app.travel.controller;

import com.travel_app.travel.converter.UserConverter;
import com.travel_app.travel.dto.Root;
import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.service.impl.UserService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;


@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    @PostMapping("/loginWithEmail")
    public ResponseEntity<?> checkLogin(@RequestParam String email, @RequestParam String password) {
        UserDto user = userService.findUserByEmail(email);
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

    @GetMapping("/loginWithGoogle")
    public ResponseEntity<?> currentUser(OAuth2AuthenticationToken oAuth2AuthenticationToken) throws IOException {
        Root root = userService.toPerson(oAuth2AuthenticationToken.getPrincipal().getAttributes());
        UserDto userDto = userConverter.toDto(root);
        if (userService.isEmailUnique(userDto.getEmail())) {
            userService.save(userDto);
        }
        return ResponseEntity.ok(userDto);
    }



    @PostMapping("/uploadAvatar/{userId}")
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


}
