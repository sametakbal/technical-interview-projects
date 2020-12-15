package com.sametakbal.api.dao.interfaces;

import com.sametakbal.api.entity.User;

public interface IUserDao extends IDao<User>{
    public User login(String username,String password);
}
