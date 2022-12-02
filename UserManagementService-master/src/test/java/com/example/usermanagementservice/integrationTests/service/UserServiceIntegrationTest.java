package com.example.usermanagementservice.integrationTests.service;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceIntegrationTest {
    @Autowired
    private UserServiceImpl service;



    @Test
    public void addUser_success() throws Exception {
        User user = new User("First","Last",80);
        User expected = service.addUser(user);
        assertThat(user.getName()).isEqualTo("First");
        assertThat(user.getSurname()).isEqualTo("Last");
    }

    @Test
    public void updateUser_success() throws Exception {
        User user = service.addUser(new User("Second","Least",90));//create a user and return the user with the id given by DB
        service.updateUser(new User(user.getId(), "NoName","Least",90));//update the user name
        Optional<User> expected = service.getUserById(user.getId());//return the updated user
        assertThat(expected.get().getName()).isEqualTo("NoName");
    }

    @Test
    public void returnUserById_success() throws Exception {
        User user= service.addUser(new User("Third","Gobert",45));
        Optional<User> expected = service.getUserById(user.getId());
        assertThat(expected).isNotNull();
        assertThat(Optional.of(expected.get().getName())).isEqualTo(Optional.of("Third"));
    }

    @Test
    public void returnListOfUsersByName_success() throws Exception {
        User user1= service.addUser(new User("John","Gobert",45));
        User user2= service.addUser(new User("John","Pag",23));
        List<User> users = service.getUserByName("John");
        assertThat(users).isNotNull();
        assertThat(Optional.of(users.get(0).getName())).isEqualTo(Optional.of("John"));
        assertThat(Optional.of(users.get(1).getName())).isEqualTo(Optional.of("John"));
        assertThat(users.size()).isEqualTo(2);
    }
    @Test
    public void  returnAllUsers_success()  throws Exception {
        List<User> users = service.getAllUsers();
        assertThat(users).isNotNull();
    }

    @Test
    public void deleteUserById_success() throws Exception {
        User user= service.addUser(new User("Vat","Tav",45));
        assertAll(()->service.deleteUser(user.getId()));

    }



}




