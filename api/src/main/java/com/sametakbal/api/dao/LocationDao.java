package com.sametakbal.api.dao;

import com.sametakbal.api.dao.interfaces.ILocationDao;
import com.sametakbal.api.entity.Location;
import com.sametakbal.api.entity.User;
import org.xml.sax.helpers.LocatorImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao extends Dao implements ILocationDao {
    @Override
    public List<Location> get(String term) {
        String q = "select * from location";
        List<Location> list = new ArrayList<>();
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                list.add(fromResultSet(rs));
                System.out.println();
            }
            pst.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    @Override
    public int add(Location location) {
        String q = "insert into location (id,location) values (default,?)";
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            pst.setString(1, location.getLocation());
            pst.executeQuery();
            pst.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    @Override
    public int update(Location location) {
        String q = "update location set location=? where id=?";
        try {
            PreparedStatement pst = this.getConn().prepareStatement(q);
            pst.setString(1, location.getLocation());
            pst.setInt(2, location.getId());
            pst.executeQuery();
            pst.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 1;
    }

    @Override
    public int remove(int id) {
        String q ="delete from location where id = ?";
        try (PreparedStatement pst = this.getConn().prepareStatement(q)) {
            pst.setInt(1, id);
            pst.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Location fromResultSet(ResultSet rs) throws SQLException{
        return new Location(rs.getInt("id"),rs.getString("location"));
    }
}
