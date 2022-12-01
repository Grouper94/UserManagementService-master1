package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {
   // @InjectMocks
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
    public void returnAllUsers_success() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity <List<User>> expected = userController.findAll() ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.hasBody());

    }
//
//        Matchers Matchers = new Matchers();
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/crud/findAll")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//              // .andExpect(jsonPath("$", hasSize(3)))
//               // .andExpect((ResultMatcher) jsonPath("$",hasSize(3)))
//               // .andExpect( jsonPath("$[2].name",is("John")));
//                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("John")));



    @Test
    public void returnUserById_success() throws Exception {
        Mockito.when(userService.getUser(3)).thenReturn(Optional.ofNullable(user1));
        ResponseEntity<Optional<User>> expected = userController.findUser(user1.getId()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody().get().getId()).isEqualTo(3);
    }

//        Mockito.when(userService.getUser(user1.getId())).thenReturn(java.util.Optional.of(user1));
//
//        Matchers Matchers = new Matchers();
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .get("/crud/findUserById/3")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("George")));


    @Test
    public void returnListOfUsersByName_success() throws Exception {
        List<User> userList = new ArrayList<>(Arrays.asList(user1,user2));
        Mockito.when(userService.getUserByName(user1.getName())).thenReturn(userList);
        ResponseEntity<List<User>> expected = userController.findUserByName(user1.getName()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody().get(0).getName()).isEqualTo(user1.getName());
        assertThat(expected.getBody().get(1).getName()).isEqualTo(user2.getName());

    }
//           Mockito.when(userService.getUserByName(user2.getName())).thenReturn((users));
//
//        Matchers Matchers = new Matchers();
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/crud/findUserByName/George")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("George")));
//    }



    @Test
    public void deleteUserById_success() throws Exception {
        doNothing().when(userService).deleteUser((user1).getId());
        ResponseEntity<Optional<String>> expected = userController.deleteUser(user1.getId()) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }
//    @Test
//    public void deleteUserById_failure() throws Exception {
//        doNothing().when(userService).deleteUser((user1).getId());
//        ResponseEntity<Optional<String>> expected = userController.deleteUser(5566) ;
//        assertThat(expected.getStatusCodeValue()).isEqualTo(500);
//        assertThat(expected.getBody()).isEqualTo("Id not Found");
//    }

//            Mockito.when(userService.getUser(user1.getId())).thenReturn(Optional.of(user1));
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/crud/2")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());

    @Test
    public void addUser_success() throws Exception {

        Mockito.when(userService.addUser(user1)).thenReturn(user1);
        ResponseEntity<Void> expected = userController.addUser(user1) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }

    @Test
    public void updateUser_success() throws Exception {
        doNothing().when(userService).updateUser(user1);
        ResponseEntity<Void> expected = userController.updateUser(user1) ;
        assertThat(expected.getStatusCodeValue()).isEqualTo(200);
        assertThat(expected.getBody()).isNull();
    }
//    User user  = new User (10,"Rick","Morty",43);
//
//        Mockito.when(userService.addUser(user)).thenReturn(user);
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
//                .post("/crud/AddUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(user));
//
//        mockMvc.perform(mockRequest)
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.status().isOk());






}