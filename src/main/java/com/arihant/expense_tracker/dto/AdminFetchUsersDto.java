package com.arihant.expense_tracker.dto;

public class AdminFetchUsersDto {

    private String username;
    private String email;
    private String role;

    public AdminFetchUsersDto(){
    }

    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
