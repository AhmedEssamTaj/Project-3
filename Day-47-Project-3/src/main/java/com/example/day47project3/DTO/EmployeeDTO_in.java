package com.example.day47project3.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeDTO_in {

    @NotEmpty(message = "username cannot be empty")
    @Size(min = 4, max = 10, message = "username length must be between 4 and 10")
    private String username;

    @NotEmpty(message = "password cannot be empty")
    @Size(min = 6, message = "password must at least be 6 characters long")
    private String password;

    @NotEmpty(message = "name cannot be empty")
    @Size(min = 2, max = 20, message = "name length must be between 2 and 20")
    private String name;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email be a valid format")
    private String email;

    @NotEmpty(message = "position cannot be empty")
    private String position;

    @NotNull(message = "salary cannot be null")
    @Positive(message = "salary must be positive")
    private Double salary;
}
