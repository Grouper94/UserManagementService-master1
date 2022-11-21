package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    MockMvc mockMvc ;

    @Autowired
    ObjectMapper mapper ;

    //@MockBean
   // UserRepository userRepository ;

    @MockBean
    UserService userService ;

    User user1 = new User (3,"Andreas","Pag",77);
    User user2 = new User (4,"George","Dap",55);
    User user3 = new User (5,"John","Voxer",23);

    List<User> users = new ArrayList<>(Arrays.asList(user1,user2,user3));

    @Test
    public void getAll() throws Exception {


        Mockito.when(userService.getAllUsers()).thenReturn(users);

        Matchers Matchers = new Matchers();
        mockMvc.perform(MockMvcRequestBuilders
                .get("/crud/findAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
              // .andExpect(jsonPath("$", hasSize(3)))
               // .andExpect((ResultMatcher) jsonPath("$",hasSize(3)))
               // .andExpect( jsonPath("$[2].name",is("John")));
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("John")));
    }

    @Test
    public void getUserById() throws Exception {
        Mockito.when(userService.getUser(user1.getId())).thenReturn(java.util.Optional.of(user1));

        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/crud/findUserById/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Andreas")));








    }







}