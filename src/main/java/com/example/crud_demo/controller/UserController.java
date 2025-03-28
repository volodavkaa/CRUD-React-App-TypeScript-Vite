package com.example.crud_demo.controller;

import com.example.crud_demo.model.User;
import com.example.crud_demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173") // Дозволяє запити з фронтенду
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // CREATE
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // READ (отримати всіх)
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // READ (отримати одного)
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            return ResponseEntity.ok(userRepository.save(user));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
