package com.example.usermanagementservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "userdb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer id ;

    @Size(min= 0 ,max=100)
    @Column(name="firstname")
     private String name ;

    @Size(min= 0 ,max=100)
     private String surname ;

    private int age ;


}
