package com.example.usermanagementservice.integrationTests.service;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceIntegrationTest {
    @Autowired
    private UserServiceImpl service;

        @Test
    public void  returnAllUsers_success()  throws Exception {
        List<User> users = service.getAllUsers();
        assertThat(users).isNotNull();
    }


    @Test
    public void returnUserById_success() throws Exception {
        Optional<User> expected = service.getUser(60549);
        assertThat(expected).isNotNull();
        assertThat(Optional.of(expected.get().getName())).isEqualTo(Optional.of("John"));
    }

    @Test
    public void returnListOfUsersByName_success() throws Exception {
        List<User> users = service.getUserByName("John");
        assertThat(users).isNotNull();
        assertThat(Optional.of(users.get(0).getName())).isEqualTo(Optional.of("John"));
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void addUser_success() throws Exception {
     User user = new User(1,"First","Last",80);
     User expected = service.addUser(user);
        assertThat(user.getName()).isEqualTo("First");
        assertThat(user.getSurname()).isEqualTo("Last");
    }

    @Test
    public void updateUser_success() throws Exception {
            service.updateUser(new User(60547,"NoName","YesName",97));
            Optional<User> user = service.getUser(60547);
        assertThat(user.get().getName()).isEqualTo("NoName");
    }

    @Test
    public void deleteUserById_success() throws Exception {
     //   assertThat(service.deleteUser(60548) );
        assertAll(()->service.deleteUser(60550));
      // Optional<User> user = service.getUser(60547);
       // assertThat(user.get().getName()).isEqualTo("NoName");
    }



}




