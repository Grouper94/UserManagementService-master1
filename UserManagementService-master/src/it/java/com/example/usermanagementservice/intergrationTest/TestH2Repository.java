package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TestH2Repository extends JpaRepository<User,Integer> {
//    @Query("SELECT * FROM Thing t WHERE t.fooIn = ?1 AND t.bar = ?2")
//    ThingEntity findByFooInAndBar(String fooIn, String bar);
//
//    SELECT * FROM Customers
//    WHERE Country='Mexico';
   // List<ResponseEntity<User>>
   User  findByName(String name);
}