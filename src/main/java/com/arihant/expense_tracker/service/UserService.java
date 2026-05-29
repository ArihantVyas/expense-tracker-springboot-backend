package com.arihant.expense_tracker.service;

import com.arihant.expense_tracker.dto.AdminFetchUsersDto;
import com.arihant.expense_tracker.dto.UserRegisterDto;
import com.arihant.expense_tracker.entity.User;
import com.arihant.expense_tracker.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        newUser.setRole("USER");

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
        user.setRole("USER");

        userRepository.save(user);

        return "User Updated";
    }

    public List<AdminFetchUsersDto> getAllUsersForAdmin(){
        List<User> users = userRepository.findAll();
        List<AdminFetchUsersDto> usersDtoList = new ArrayList<>();

        for(User user : users){
            AdminFetchUsersDto dto = new AdminFetchUsersDto();
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
            dto.setRole(user.getRole());

            usersDtoList.add(dto);
        }

        return usersDtoList;

    }


}
