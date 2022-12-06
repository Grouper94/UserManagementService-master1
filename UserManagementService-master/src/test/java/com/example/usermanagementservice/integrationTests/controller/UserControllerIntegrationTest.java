package com.example.usermanagementservice.integrationTests.controller;

import com.example.usermanagementservice.controller.UserController;
import com.example.usermanagementservice.controller.UserControllerImpl;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerIntegrationTest {
    @InjectMocks
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
    public void addUser_success() throws Exception {

        User user  = new User (10,"Rick","Morty",43);

        Mockito.when(userService.addUser(user)).thenReturn(user);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/crud/AddUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void updateUser_success() throws Exception {
        doNothing().when(userService).updateUser(user1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/crud/Update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user1));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void returnUserById_success() throws Exception {


        Mockito.when(userService.getUserById(user3.getId())).thenReturn(java.util.Optional.of(user3));

        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserById/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("John")));
    }

    @Test
    public void returnListOfUsersByName_success() throws Exception {
        List<User> usersList = new ArrayList<>(Arrays.asList(user1,user2));

        Mockito.when(userService.getUserByName(user2.getName())).thenReturn((usersList));

        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserByName/George")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("George")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("George")));
    }

    @Test
    public void returnAllUsers_success() throws Exception {
        Mockito.when(userService.getAllUsers()).thenReturn((users));
        Matchers Matchers = new Matchers();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name", Matchers.is("John")));
    }
    @Test
    public void deleteUserById_success() throws Exception {
            Mockito.when(userService.getUserById(user1.getId())).thenReturn(Optional.of(user1));

              mockMvc.perform(MockMvcRequestBuilders
                        .delete("/crud/Delete/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }











}