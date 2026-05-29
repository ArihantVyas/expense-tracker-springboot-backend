package com.arihant.expense_tracker.controller;

import com.arihant.expense_tracker.dto.AdminFetchUsersDto;
import com.arihant.expense_tracker.dto.UserRegisterDto;
import com.arihant.expense_tracker.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserAuthController {

    private UserService userService;

    public UserAuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String createNewUser(@RequestBody @Valid UserRegisterDto registerDto){

        return userService.registerUser(registerDto);
    }

    @DeleteMapping("/delete-usr")
    public String deleteUser(){
        return userService.deleteUser();
    }

    @PatchMapping("/update-usr")
    public String updateUser(@RequestBody UserRegisterDto updUsr){
        return userService.updateUser(updUsr);
    }

    @GetMapping("/get-all-users")
    public List<AdminFetchUsersDto> getAllUsersForAdmin(){
        return userService.getAllUsersForAdmin();
    }


}
