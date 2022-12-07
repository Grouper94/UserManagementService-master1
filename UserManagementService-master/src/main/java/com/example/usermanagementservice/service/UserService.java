package com.example.usermanagementservice.service;

import com.example.usermanagementservice.model.User;
import java.util.List;
import java.util.Optional;


public interface UserService {

    User addUser(User user) throws Exception ;
    void updateUser(User user) throws Exception;
     void addXRandomUsers(int X) throws Exception;


    Optional<User> getUserById(Integer id) throws Exception ;
    List<User> getUserByName(String name) throws Exception;
    List<User> getAllUsers() throws Exception;


    void deleteAllUsers() throws Exception ;
    void deleteUser(int id) throws Exception;
}

