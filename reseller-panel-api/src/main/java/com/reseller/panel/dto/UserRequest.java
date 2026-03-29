package com.reseller.panel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 20, message = "Username must be 3–20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Only letters, numbers and underscore allowed")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 20, message = "Password must be 4–20 characters")
    private String password;

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "ADMIN|RESELLER", message = "Role must be ADMIN or RESELLER")
    private String role;
}