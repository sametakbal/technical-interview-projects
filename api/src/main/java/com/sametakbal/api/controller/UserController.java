package com.sametakbal.api.controller;


import com.sametakbal.api.entity.Dto.LoginDto;
import com.sametakbal.api.entity.User;
import com.sametakbal.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        path = "/api/users"
)
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public List<User> fetchUsers(@RequestHeader("Token") String token) {
        return userService.selectUsers(null);
    }

    @RequestMapping(
            path = "login",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = userService.loginWithUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
        return token != null ? ResponseEntity.ok(token) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username or Email Wrong!");
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean register(@RequestBody User user) {
        return userService.addUser(user);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean update(@RequestBody User user, @RequestHeader("Token") String token) {
        return userService.update(user,token);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}"
    )
    public boolean deleteUser(@PathVariable("id") int id, @RequestHeader("Token") String token) {
        return userService.deleteUser(id,token);
    }
}
