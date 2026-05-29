package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.UserRegisterDto;
import com.arihant.expense_tracker.entity.User;
import com.arihant.expense_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder,UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User getAuthenticatedUser(){
        Authentication authentication = SecurityContextHolder.
                getContext()
                .getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username).orElseThrow();
    }

    public String registerUser(UserRegisterDto registerDto){

        User newUser = new User();

        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        newUser.setPassword(registerDto.getPassword());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        userRepository.save(newUser);

        return "User registered successfully";
    }

    public String deleteUser(){

        User user = getAuthenticatedUser();
        userRepository.delete(user);

        return "User deleted";
    }

    public String updateUser(UserRegisterDto updUsr){
        User user = getAuthenticatedUser();

        user.setUsername(updUsr.getUsername());
        user.setPassword(passwordEncoder.encode(updUsr.getPassword()));
        user.setEmail(updUsr.getEmail());

        userRepository.save(user);

        return "User Updated";
    }


}
