package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/crud")
public class UserControllerImpl implements UserController{
    @Autowired
    private UserService userService ;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping("/findAll")
    @Operation(tags = {"Note"},
                summary = "Returns All The Users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Found All The Users"),
            @ApiResponse(responseCode = "400",description = "Error"),
            @ApiResponse(responseCode = "404",description = "No Users Found")
    })

    public ResponseEntity<List<User>> findAll() {
        List <User> users;
        try {
            //return userService.getAllUsers();
            users = userService.getAllUsers();
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
    @Override
    @GetMapping("/findUserById/{id}")
    @Operation(tags = {"Note"},
               summary = "Returns the user with the specific id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Found the User"),
            @ApiResponse (responseCode = "400",description = "Invalid id supplied"),
            @ApiResponse(responseCode = "404", description = "User Not Found")
    })

    public ResponseEntity <Optional<User>> findUser(@Parameter(description = "id of User to be searched",example="1") @PathVariable Integer id) throws SQLException {
       Optional<User> user;
        try {
            user = userService.getUser(id);
        }
        catch (Exception e ){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if(user.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Override
     @GetMapping("/findUserByName/{name}")
    @Operation(
            tags = {"Note"},
        summary = "Returns the user OR users with the specific name",
    parameters = {@Parameter(name = "name",description = "name of User/s to be searched",example="George")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Found the User"),
            @ApiResponse (responseCode = "400",description = "Invalid name supplied"),
            @ApiResponse(responseCode = "404", description = "User/s Not Found")
    })
    public ResponseEntity<List<User>> findUserByName(@PathVariable String name) {
        List<User> users;    // = new ArrayList<>();
        try {
           users = userService.getUserByName(name);
        }
        catch (SQLException e)  {

            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @Override
    @PostMapping("/AddUser")
    @Operation(
            tags = {"Note"},
            summary = "Creates  a user by giving user's attributes")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User has been Added Successfully"),
            @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
    })
    public ResponseEntity<Void> addUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to add",required = true,content = @Content(schema=@Schema(implementation = User.class)))
                            @Valid User user) {

        try {
              userService.addUser(user);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @Override
    @PutMapping("/update")
    @Operation(tags = {"Note"},
    summary = "Update an EXISTING user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User has been UPDatAded Successfully"),
            @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "User has Not Been  uPDATED")
    })

    public ResponseEntity<Void> updateUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Put an existing ID",required = true,content = @Content(schema=@Schema(implementation = User.class)))
                               @Valid User user) {

        try {
            userService.updateUser(user);
        } catch (SQLException e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
    @Override
    @DeleteMapping("/{id}")
    @Operation(tags = {"Note"},
    summary = "Deletes a user with the specific id", parameters = {@Parameter(name = "id",description = "id of User to be deleted",example="1")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User has been Deleted Successfully"),
            @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "User has Not Been  deleted")
    })

    public ResponseEntity<Optional<String>> deleteUser(@PathVariable int id) {
        Optional <String> msg = Optional.empty();
        String ms = "Id not Found";
        try {
            userService.deleteUser(id);
        } catch (SQLException e) {
            msg = Optional.ofNullable(ms); ;
            System.out.println(msg);
            return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
