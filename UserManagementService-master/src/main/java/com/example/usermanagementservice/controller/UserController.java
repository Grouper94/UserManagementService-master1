package com.example.usermanagementservice.controller;


import com.example.usermanagementservice.model.User;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

public interface UserController {

     ResponseEntity<List<User>> findAll()throws Exception;
     ResponseEntity<Optional<User>> findUser(Integer id) throws Exception;
     ResponseEntity<List<User>> findUserByName(String name)throws Exception;
     ResponseEntity<Void> addUser(User user)throws Exception;
     ResponseEntity<Void> updateUser(User user) throws Exception;
     ResponseEntity<Optional<String>> deleteUser(int id)throws Exception;
}
