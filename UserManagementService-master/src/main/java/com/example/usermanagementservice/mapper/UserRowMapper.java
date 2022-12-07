package com.example.usermanagementservice.mapper;

import com.example.usermanagementservice.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet ;
import java.sql.SQLException;


public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs,int rownum) throws SQLException
    {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("firstname"));
        user.setSurname(rs.getString("surname"));
        user.setAge(rs.getInt("age"));
        return user ;
    }

}
