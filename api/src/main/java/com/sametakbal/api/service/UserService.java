package com.sametakbal.api.service;

import com.sametakbal.api.dao.UserDao;
import com.sametakbal.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> selectUsers(String term){
        return userDao.get(term);
    }

    public boolean addUser(User user) {
        return userDao.add(user) == 1;
    }

    public boolean update(User user) {
        return userDao.update(user) == 1;
    }

    public boolean deleteUser(int id) {
        return userDao.remove(id) == 1;
    }
}
