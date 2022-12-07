package com.example.usermanagementservice.integrationTests.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.example.usermanagementservice.UserManagementServiceApplication;
import com.example.usermanagementservice.controller.UserController;
import com.example.usermanagementservice.controller.UserControllerImpl;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
//@TestMethodOrder(OrderAnnotation.class)
class UserControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    private TestH2Repository h2Repository;

    User user = new User( "Rick", "Morty", 43);
    User user1 = new User( "Rick", "Robinson", 76);
    User user2 = new User( "Gobert", "Jonson", 23);

    @BeforeEach
    void init() {
        h2Repository.save(user);
        h2Repository.save(user1);
        h2Repository.save(user2);
    }

    @AfterEach
    void teardown() {
       h2Repository.deleteAll();
    }
    @Test
    public void addUser_success() throws Exception {
        User usr = new User( "NewUser", "Created", 65);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/crud/AddUser")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(usr));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
        User actual = h2Repository.findByName("NewUser");
        assertEquals(usr.getName(), actual.getName());
    }


    @Test
    public void updateUser_success() throws Exception {
       User actual = h2Repository.save(user);
        actual.setName("Changed");

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/crud/Update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(actual));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.status().isOk());
        User usr = h2Repository.findByName("Changed");
        assertEquals(actual.getName(), usr.getName());

    }


    @Test
    public void returnUserById_success() throws Exception {

        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserById/3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(3)));
    }


    @Test
    public void returnListOfUsersByName_success() throws Exception {


        Matchers Matchers = new Matchers();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findUserByName/Rick")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", org.hamcrest.Matchers.is("Rick")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", org.hamcrest.Matchers.is("Rick")))
                .andExpect(MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)));

    }


    @Test
    public void returnAllUsers_success() throws Exception {

        Matchers Matchers = new Matchers();
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/crud/findAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3)));

    }

    @Test
    public void deleteUserById_success() throws Exception {
      // h2Repository.save()
        System.out.println(h2Repository.findAll());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/crud/Delete/7")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
