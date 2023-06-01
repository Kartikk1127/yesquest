package com.Kartikey.testbackend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users")
@Data
public class User {

//    --> Name. Email, Password, Role (User and Admin)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

}
