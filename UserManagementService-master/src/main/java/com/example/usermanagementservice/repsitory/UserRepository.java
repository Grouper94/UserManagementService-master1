package com.example.usermanagementservice.repsitory;
import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
//@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {


    public Optional<User> findById(Integer id);
    public List<User> findAll();
    public List<User> findByName(String name);
    public void deleteById(Integer id);

    public User save(User user);




}
