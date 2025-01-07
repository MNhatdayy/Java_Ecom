package com.HutechB6.Ecommerce.controller;

import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.Role;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.service.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private final UserDetailsServiceImp userDetailsServiceImp;

    public UserController(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userDetailsServiceImp.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userDetailsServiceImp.findAllUsers().stream()
                .filter(user -> user.getRole() == Role.USER)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
@GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userDetailsServiceImp.findUserById(id).orElse(null);
        return ResponseEntity.ok(user);
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        var updatedUser = userDetailsServiceImp.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }
}
