package com.example.usermanagementservice.controller;


import com.example.usermanagementservice.model.User;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserController {

    public ResponseEntity<List<User>> findAll()throws SQLException;
    public ResponseEntity<Optional<User>> findUser(Integer id) throws SQLException;
   public ResponseEntity<List<User>> findUserByName(String name)throws SQLException;
    public ResponseEntity<Void> addUser(User user)throws SQLException;

    public ResponseEntity<Void> updateUser(User user) throws SQLException;
    public ResponseEntity<Optional<String>> deleteUser(int id)throws SQLException;
}
