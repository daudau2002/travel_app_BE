package com.travel_app.travel.api;

import com.travel_app.travel.dto.UserDto;
import com.travel_app.travel.dto.UsernameExistsException;
import com.travel_app.travel.entity.UserEntity;
import com.travel_app.travel.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class UserAPI {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/createUser")
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return ResponseEntity.ok("User registered successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "editUser/{id}")
    public ResponseEntity<String> editUser(@RequestBody UserDto userDto, @PathVariable("id") long id) {
        try {
            userDto.setId(id);
            userService.update(userDto);
            return ResponseEntity.ok("User updated successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/getUserByUsername")
    public UserDto findUserByUsername(@RequestParam String username){
        return userService.findUserByUsername(username);
    }

    @GetMapping(value = "/getUserByEmail")
    public UserDto findUserByEmail(@RequestParam String email){
        return userService.findUserByEmail(email);
    }
    @GetMapping(value = "/getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser() {
        try {
            List<UserDto> users = userService.getAllUser();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
