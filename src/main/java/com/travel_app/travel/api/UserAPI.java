package com.travel_app.travel.api;

import com.travel_app.travel.dto.UserDto;
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
    public ResponseEntity<String> createUser(@RequestBody UserDto userDto) {
        try {
            userService.save(userDto);
            return ResponseEntity.ok("User registered successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "edit/{id}")
    public ResponseEntity<String> editUser(@RequestBody UserDto userDto, @PathVariable("id") long id) {
        try {
            userDto.setId(id);
            userService.update(userDto);
            return ResponseEntity.ok("User updated successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UsernameExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/getUserByUsername")
    public UserDto findOneUser(@RequestParam String username){
        return userService.findUserByUsername(username);
    }

}
