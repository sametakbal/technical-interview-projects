package com.sametakbal.api.dao;

import com.sametakbal.api.dao.interfaces.IUserDao;
import com.sametakbal.api.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class UserDao extends Dao implements IUserDao {
    @Override
    public List<User> get(String term) {
        String q = "select * from users";
        List<User> list = new ArrayList<>();
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(fromResultSet(rs));
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public int add(User user) {
        String q = "insert into users (id,fullname,username,email,password) values (default,?,?,?,?)";
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            pst.setString(1, user.getFullName());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.executeQuery();
            pst.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    @Override
    public int update(User user) {
        String q = "update users set fullname=?,username=?,email=?,password=? where id=?";
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            pst.setString(1, user.getFullName());
            pst.setString(2, user.getUsername());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setInt(5, user.getId());
            pst.executeQuery();
            pst.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    @Override
    public int remove(int id) {
        String q = "delete from users where id = ?";
        try (PreparedStatement pst = this.getConn().prepareStatement(q)) {
            pst.setInt(1, id);
            pst.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    public User fromResultSet(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("fullname"), rs.getString("username"),
                rs.getString("email"), rs.getString("password"), rs.getBoolean("isAdmin"),
                rs.getDate("created_on"), rs.getDate("last_login"));
    }

    @Override
    public User login(String username, String password) {
        String q = "select * from users where (email=? or username=?) and password=?";
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2, username);
            pst.setString(3, password);
            ResultSet rs = pst.executeQuery();
            if (rs.next()){
                return fromResultSet(rs);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
