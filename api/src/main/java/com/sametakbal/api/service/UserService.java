package com.sametakbal.api.service;

import com.sametakbal.api.controller.manager.TokenManager;
import com.sametakbal.api.dao.UserDao;
import com.sametakbal.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    private final TokenManager tokenManager;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
        this.tokenManager = new TokenManager();
    }

    public List<User> selectUsers(String term){
        return userDao.get(term);
    }

    public String loginWithUsernameAndPassword(String username,String password){
        User user = userDao.login(username,password);
        if ( user != null){
            return tokenManager.getToken(user.getId());
        }
        return null;
    }

    public boolean addUser(User user) {
        return userDao.add(user) == 1;
    }

    public boolean update(User user,String token) {
        if(!token.isEmpty()&& tokenManager.isExpired(token)){
            return userDao.update(user) == 1;
        }
        return false;
    }

    public boolean deleteUser(int id,String token) {
        if(!token.isEmpty()&& tokenManager.isExpired(token)){
            return userDao.remove(id) == 1;
        }
        return false;
    }
}
