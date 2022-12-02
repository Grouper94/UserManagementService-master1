package com.example.usermanagementservice.unitTests.service;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository userRepository;

    @Test
    public void addUser_thenReturnUser() throws Exception {
        User actual = new User(1, "John", "Fek", 23);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(actual);
        User expected = service.addUser(actual);

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isEqualTo(actual.getId());
    }

    @Test
    public void updateUser_whenIdExists_thenReturnVoid() throws Exception {
        User actual = new User(1, "John", "Fek", 23);
        actual.setAge(25);

        when(userRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        when(userRepository.save(actual)).thenReturn(actual);

        service.updateUser(actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getAge()).isEqualTo(25);
    }

    @Test
    public void getUserById_whenUserExists_thenReturnUser() throws Exception {
        User actual = new User(2, "Boher", "Pappas", 54);
        when(userRepository.findById(actual.getId())).thenReturn(Optional.of(actual));
        Optional<User> expected = service.getUserById(actual.getId());
        assertThat(expected).isNotNull();
        assertThat(Optional.of(actual)).isEqualTo(expected);
    }

    @Test
    public void getUserByName_whenUsersExist_thenReturnListOfUsers() throws Exception {
        User user1 = new User(1, "Boher", "Pappas", 54);
        User user2 = new User(2, "Boher", "Palson", 64);

        when(userRepository.findByName("Boher")).thenReturn(List.of(user1, user2));

        List<User> users = service.getUserByName("Boher");

        assertThat(users).isNotNull();
        assertThat(users.get(0).getName()).isEqualTo(users.get(1).getName());
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    public void getAllUsers_whenUsersExist_thenReturnListOfAllUsers() throws Exception {
        User user1 = new User(1, "John", "Fek", 23);
        User user2 = new User(2, "Boher", "Pappas", 54);

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        List<User> users = service.getAllUsers();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void deleteUserById_whenUserExists_thenReturnVoid() throws Exception {
        User actual = new User(1, "John", "Fek", 23);
        doNothing().when(userRepository).deleteById((actual).getId());
        assertAll(()->service.deleteUser(1));
    }
}



