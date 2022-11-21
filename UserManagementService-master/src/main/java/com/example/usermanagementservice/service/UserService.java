package com.example.usermanagementservice.service;
import com.example.usermanagementservice.model.User;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    //create
    void addUser(User user) throws SQLException ;

    //read
    Optional<User> getUser(Integer id);
    List<User> getAllUsers() throws SQLException;
    List<User> getUserByName(String name) throws SQLException;

    //update
    void updateUser(User user) throws Exception;

    //delete
    void deleteUser(int id) throws SQLException;
}
