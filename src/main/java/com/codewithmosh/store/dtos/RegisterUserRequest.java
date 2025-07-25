package com.codewithmosh.store.dtos;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Email in required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min =6 , max = 25,message = "Password must be between 6 to 25 characters long")
    private String password;
}
