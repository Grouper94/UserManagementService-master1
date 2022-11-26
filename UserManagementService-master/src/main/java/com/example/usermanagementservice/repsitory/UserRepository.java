package com.example.usermanagementservice.repsitory;
import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository  extends JpaRepository<User,Integer> {


     Optional<User> findById(Integer id);
     List<User> findAll();
     List<User> findByName(String name);
     void deleteById(Integer id);
    <S extends User> Iterable<S>  save(Iterable<S> user);





}
