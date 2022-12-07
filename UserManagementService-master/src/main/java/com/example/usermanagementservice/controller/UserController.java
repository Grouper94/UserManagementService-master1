package com.example.usermanagementservice.controller;


import com.example.usermanagementservice.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

public interface UserController {


     @Operation(
             tags = {"Note"},
             summary = "Creates  a user by giving user's attributes")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "User has been Added Successfully"),
             @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
             @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
     })
     ResponseEntity<Void> addUser(User user) throws Exception;
     ResponseEntity<Void> updateUser(User user) throws Exception;

     ResponseEntity<Void> addXRandomUsers(int X) throws Exception;


     ResponseEntity<Optional<User>> findUserById(Integer id) throws Exception;
     ResponseEntity<List<User>> findUserByName(String name) throws Exception;
     ResponseEntity<List<User>> findAll()throws Exception;


     ResponseEntity<Optional<String>> deleteUser(int id) throws Exception;
     ResponseEntity<Optional<String>> deleteAllUsers() throws Exception;


}
