package com.example.usermanagementservice.repsitory;
import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User,Integer> {
    public List<User> findByName(String name);
    public void deleteById(Integer id);

}
