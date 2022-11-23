package com.example.usermanagementservice.service;

import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class  UserServiceImpl implements UserService {
   private UserRepository userRepository ;

//@Autowired
//    UserServiceImpl(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public User addUser(User user) throws SQLException {
       return  userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(Integer id) {
      return userRepository.findById(id);
    }

    @Override
    public List<User> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUserByName(String name) throws SQLException{
      return userRepository.findByName(name);

    }


    @Override
    public void updateUser(User user) throws Exception {

    //boolean exists = userRepository.existsById(user.getId());
    if (userRepository.findById(user.getId()).isPresent())
        userRepository.save(user);
    else
        throw new Exception("Put an Id that Already Exists");
    }


    @Override
    public void deleteUser(int id) throws SQLException {
        userRepository.deleteById(id);
    }

    public void setUserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
