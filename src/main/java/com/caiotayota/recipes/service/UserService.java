package com.caiotayota.recipes.service;

import com.caiotayota.recipes.exceptions.UsernameExistsException;
import com.caiotayota.recipes.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.caiotayota.recipes.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {
    
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    public void registerNewUser(User user) {
        
        Optional<User> userFromDb = userRepo.findById(user.getEmail());
        if (userFromDb.isPresent()) throw new UsernameExistsException();
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }
    
}
