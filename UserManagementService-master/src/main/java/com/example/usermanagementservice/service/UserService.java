package com.example.usermanagementservice.service;
import com.example.usermanagementservice.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;


public interface UserService {
    //create
    User addUser(User user) throws Exception ;

    //read
    Optional<User> getUser(Integer id);
    List<User> getAllUsers() throws Exception;
    List<User> getUserByName(String name) throws Exception;

    //update
    void updateUser(User user) throws Exception;

    //delete
    void deleteUser(int id) throws Exception;
}
