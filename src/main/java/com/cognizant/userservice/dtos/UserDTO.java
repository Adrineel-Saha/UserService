package com.cognizant.userservice.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;

    @NotBlank(message="User Name Cannot be blank")
    @Size(min = 3, max = 50, message = "User Name must be between 3 to 50 characters")
    private String userName;

    @Email(message="Please enter a valid email")
    @NotBlank(message="Email cannot be blank")
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
