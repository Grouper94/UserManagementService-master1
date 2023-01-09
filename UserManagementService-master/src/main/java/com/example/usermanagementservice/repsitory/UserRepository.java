package com.example.usermanagementservice.repsitory;

import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository< User, Integer > {

    < S extends User > Iterable< S > save( Iterable< S > user ) throws IllegalArgumentException;

    Optional< User > findById( Integer id ) throws IllegalArgumentException;

    List< User > findAll() throws IllegalArgumentException;

    List< User > findByName( String name ) throws IllegalArgumentException;

    void deleteById( Integer id ) throws IllegalArgumentException;

    void deleteAll() throws IllegalArgumentException;

}
