package com.example.usermanagementservice.controller;


import com.example.usermanagementservice.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
     ResponseEntity<Void> addUser(String name,String surname,int age) throws Exception;

     @Operation(tags = {"Note"},
             summary = "Update an EXISTING user's data")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "User has been UPDatAdded Successfully"),
             @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
             @ApiResponse(responseCode = "502", description = "Id Does Not exist")
     })
     ResponseEntity<Void> updateUser(User user) throws Exception;


     @Operation(
             tags = {"Note"},
             summary = "Creates  x  users ",
           parameters =  @Parameter(description = "How many Users Do You Want To Create??",example="4"))

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Users has been Added Successfully"),
             @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
             @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
     })
     ResponseEntity<Void> addXRandomUsers(int X) throws Exception;

     @Operation(tags = {"Note"},
             summary = "Returns the user with the specific id",
            parameters = {@Parameter(description = "id of User to be searched",example="1")})

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found the User"),
             @ApiResponse (responseCode = "400",description = "Invalid id supplied"),
             @ApiResponse(responseCode = "404", description = "User Not Found")
     })
     ResponseEntity<Optional<User>> findUserById(Integer id) throws Exception;

     @Operation(
             tags = {"Note"},
             summary = "Returns the user OR users with the specific name",
             parameters = {@Parameter(name = "name",description = "name of User/s to be searched",example="George")})
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found the User"),
             @ApiResponse (responseCode = "400",description = "Invalid name supplied"),
             @ApiResponse(responseCode = "404", description = "User/s Not Found")
     })
     ResponseEntity<List<User>> findUserByName( String name) throws Exception;

     @Operation(tags = {"Note"},
             summary = "Returns All The Users")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found All The Users"),
             @ApiResponse(responseCode = "400",description = "Error"),
             @ApiResponse(responseCode = "404",description = "No Users Found")
     })
     ResponseEntity<List<User>> findAll()throws Exception;

     @Operation(tags = {"Note"},
             summary = "Deletes a user with the specific id", parameters = {@Parameter(name = "id", description = "id of User to be deleted", example = "1")})
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "User has been Deleted Successfully"),
             //  @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
             //  @ApiResponse(responseCode = "404", description = "User has Not Been  deleted"),
             @ApiResponse(responseCode = "500", description = "Id Does Not Exist ")
     })
     ResponseEntity<Optional<String>> deleteUser(int id) throws Exception;

     @Operation(tags = {"Note"},
             summary = "Deletes All Users")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Users has been Deleted Successfully"),
             //  @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
             @ApiResponse(responseCode = "404", description = "Users has Not Been  deleted"),
             // @ApiResponse(responseCode = "500", description = "Id Does Not Exist ")
     })
     ResponseEntity<Optional<String>> deleteAllUsers() throws Exception;


}
