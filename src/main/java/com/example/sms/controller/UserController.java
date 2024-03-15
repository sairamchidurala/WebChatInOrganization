package com.example.sms.controller;

import com.example.sms.dto.UsersDTO;
import com.example.sms.model.User;
import com.example.sms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) {
        User user = userService.getUserById(userId)
                .orElse(null);
        return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Integer userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user1) {
        String email = user1.getEmailId();
        String password = user1.getPassword();
        User user = userService.findByEmailId(email);
        if (user != null && user.getPassword().equals(password)) {
            userService.updateOnlineStatusByEmail(email, true);
            return ResponseEntity.ok(user.getUserId() + "");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody User user1) {
        String email = user1.getEmailId();
        userService.updateOnlineStatusByEmail(email, false);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/getOnlineUsers")
    public ResponseEntity<List<UsersDTO>> getOnlineUsers(@RequestBody User user) {
        System.out.println("Got userEmail as: " + user.getEmailId());
        List<UsersDTO> users = userService.findAllOnlineUsers(user.getEmailId());
        System.out.println("Online users: " + users.toString());
        if (users.isEmpty()) {
			return ResponseEntity.ok(Collections.emptyList());
		}
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
