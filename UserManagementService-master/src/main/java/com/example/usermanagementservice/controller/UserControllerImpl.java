package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/crud")
@AllArgsConstructor
public class UserControllerImpl implements UserController{
    private final UserService userService ;

    @PostMapping("/AddUser")
    public ResponseEntity<Void>addUser(@RequestBody User user) {
        try {
            userService.addUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }


    @Override
    @PutMapping("/Update")
    @Operation(tags = {"Note"},
            summary = "Update an EXISTING user's data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "User has been UPDatAdded Successfully"),
            @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
            @ApiResponse(responseCode = "502", description = "Id Does Not exist")
    })
    public ResponseEntity<Void> updateUser(@RequestBody  User user)
    {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(HttpStatus.OK);
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
    public ResponseEntity <Optional<User>> findUserById(@Parameter(description = "id of User to be searched",example="1") @PathVariable Integer id)  {
        Optional<User> user;
        try {
            user = userService.getUserById(id);
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
        catch (Exception e)  {

            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if(users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
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
            users = userService.getAllUsers();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/Delete/{id}")
    @Operation(tags = {"Note"},
            summary = "Deletes a user with the specific id", parameters = {@Parameter(name = "id", description = "id of User to be deleted", example = "1")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User has been Deleted Successfully"),
            //  @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
            //  @ApiResponse(responseCode = "404", description = "User has Not Been  deleted"),
            @ApiResponse(responseCode = "500", description = "Id Does Not Exist ")
    })
    public ResponseEntity<Optional<String>> deleteUser(@PathVariable int id) {
        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            Optional <String> msg =Optional.of( "Id not Found");
            return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }








    @Override
    @PostMapping("/AddUsers/{X}")
    @Operation(
            tags = {"Note"},
            summary = "Creates  x  users ")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Users has been Added Successfully"),
            @ApiResponse (responseCode = "400",description = "Invalid data supplied"),
            @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
    })
    //
    // public ResponseEntity<Void>addUser(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User to add",required = true,content = @Content(schema=@Schema(implementation = User.class))) @Valid User user) {
    public ResponseEntity<Void>addXRandomUsers( @Parameter(description = "How many Users Do You Want To Create??",example="4") @PathVariable int X){

        try {
            userService.addXRandomUsers(X);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }



    @Override
    @DeleteMapping("/Delete")
    @Operation(tags = {"Note"},
            summary = "Deletes All Users")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users has been Deleted Successfully"),
            //  @ApiResponse(responseCode = "400", description = "Invalid data supplied"),
              @ApiResponse(responseCode = "404", description = "Users has Not Been  deleted"),
           // @ApiResponse(responseCode = "500", description = "Id Does Not Exist ")
    })
    public ResponseEntity<Optional<String>> deleteAllUsers() {

        try {
            userService.deleteAllUsers();
        } catch (Exception e) {
            Optional <String> msg =Optional.of( "Noooooo Waay");
            return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }
}
