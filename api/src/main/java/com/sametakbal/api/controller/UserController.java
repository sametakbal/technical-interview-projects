package com.sametakbal.api.controller;


import com.sametakbal.api.entity.User;
import com.sametakbal.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
    public List<User> fetchUsers(){
        return userService.selectUsers(null);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean register(@RequestBody User user){
        return userService.addUser(user);
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public boolean update(@RequestBody User user){
        return userService.update(user);
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            path = "{id}"
    )
    public boolean deleteUser(@PathVariable("id") int id){
        return userService.deleteUser(id);
    }
}
