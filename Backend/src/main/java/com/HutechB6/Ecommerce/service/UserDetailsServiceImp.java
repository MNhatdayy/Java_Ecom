package com.HutechB6.Ecommerce.service;

import com.HutechB6.Ecommerce.model.Product;
import com.HutechB6.Ecommerce.model.User;
import com.HutechB6.Ecommerce.repository.IUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final IUserRepository userRepository;
    public UserDetailsServiceImp(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    public void deleteUser(Long id) {
        var user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if(user.isEnabled()){
            userRepository.delete(user);
        }
    }
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
