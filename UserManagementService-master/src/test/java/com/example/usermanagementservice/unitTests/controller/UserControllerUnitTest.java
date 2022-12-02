package com.example.usermanagementservice.unitTests.controller;

import com.example.usermanagementservice.controller.UserController;
import com.example.usermanagementservice.controller.UserControllerImpl;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;


@WebMvcTest(UserController.class)
class UserControllerUnitTest {
   @Autowired
   private UserControllerImpl userController;

    @Autowired
     private MockMvc mockMvc ;

    @Autowired
    ObjectMapper mapper ;

    @MockBean
    UserService userService ;

    User user1 = new User (3,"George","Pag",77);
    User user2 = new User (4,"George","Dap",55);
    User user3 = new User (5,"John","Voxer",23);

    List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));

    @Test
    public void addUser_thenReturnValidResponse() throws Exception {
        Mockito.when(userService.addUser(user1)).thenReturn(user1);
        ResponseEntity<Void> expected = userController.addUser(user1) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }

    @Test
    public void updateUser_whenGivenIdExists_thenReturnValidResponse() throws Exception {
        doNothing().when(userService).updateUser(user1);
        ResponseEntity<Void> expected = userController.updateUser(user1) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }

    @Test
    public void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {
        Mockito.when(userService.getUserById(3)).thenReturn(Optional.ofNullable(user1));
        ResponseEntity<Optional<User>> expected = userController.findUserById(user1.getId()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody().get().getId()).isEqualTo(3);
    }

    @Test
    public void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers() throws Exception {
        List<User> userList = new ArrayList<>(Arrays.asList(user1,user2));
        Mockito.when(userService.getUserByName(user1.getName())).thenReturn(userList);
        ResponseEntity<List<User>> expected = userController.findUserByName(user1.getName()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody().get(0).getName()).isEqualTo(user1.getName());
        assertThat(expected.getBody().get(1).getName()).isEqualTo(user2.getName());

    }

    @Test
    public void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity <List<User>> expected = userController.findAll() ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.hasBody());
    }

    @Test
    public void deleteUserById_whenUserExists_thenReturnValidResponse() throws Exception {
        doNothing().when(userService).deleteUser((user1).getId());
        ResponseEntity<Optional<String>> expected = userController.deleteUser(user1.getId()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }
}