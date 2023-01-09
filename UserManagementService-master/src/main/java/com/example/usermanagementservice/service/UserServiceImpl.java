package com.example.usermanagementservice.service;

import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class  UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
     UserServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
     }


    @Override
    public User addUser(User user)  {

        return userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {

        if (userRepository.findById(user.getId()).isPresent())
            userRepository.save(user);
    }

    @Override
    public void addXRandomUsers(int X)  {

        List<String> randomNames =  new ArrayList<String>() ;

        randomNames.addAll(Arrays.asList("John", "George", "Olaf", "Andreas"));

        List<String> randomSurNames =  new ArrayList<String>() ;

        randomSurNames.addAll(Arrays.asList("Johnson", "Georgiou", "Olafson", "Andreou"));

        Random rand = new Random();

        for (int i = 0 ; i < X ; i++)
        {
            User user = new User();

            user.setName(randomNames.get(rand.nextInt(randomSurNames.size())));

            user.setSurname(randomSurNames.get(rand.nextInt(randomSurNames.size())));

            user.setAge(rand.nextInt(99));

            System.out.println(X);

            System.out.println(user.toString());

            userRepository.save(user);
        }
    }


    @Override
    public Optional<User> getUserById(Integer id)  {

        return userRepository.findById(id);

    }
    @Override
    public List<User> getAllUsers()  {

        return userRepository.findAll();
    }
    @Override
    public List<User> getUserByName(String name)  {

        return userRepository.findByName(name);
    }

    @Override
    public void deleteUser(int id)  {

        userRepository.deleteById(id);
    }
    @Override
    public void deleteAllUsers()  {

        userRepository.deleteAll();
    }

}

