package com.example.usermanagementservice.controller;


import com.example.usermanagementservice.model.User;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserController {

    public List<User> findAll();
    public Optional<User> findUser(Integer id) throws SQLException;;
    ResponseEntity<List<User>> findUserByName(String name);
    public void addUser(User user);

    public void updateUser(User user) ;
    public void deleteUser(int id);
}
