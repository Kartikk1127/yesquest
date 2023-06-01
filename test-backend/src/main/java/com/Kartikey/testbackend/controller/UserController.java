package com.Kartikey.testbackend.controller;

import com.Kartikey.testbackend.dao.UserRepository;
import com.Kartikey.testbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listUsers()
    {
        return new ArrayList<User>(userRepository.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public User getUser(@PathVariable Long id)
    {
        User tempUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("user not available with id "+ id));

        return tempUser;
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser)
    {
        User existingUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(("user not found with id" + id)));
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        existingUser.setRole(updatedUser.getRole());

        User savedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(savedUser);

    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id)
    {
        User tempUser = userRepository.findById(id)
                .orElseThrow(()-> new RuntimeException(("user not found with id" + id)) );
        userRepository.delete(tempUser);
    }
}
