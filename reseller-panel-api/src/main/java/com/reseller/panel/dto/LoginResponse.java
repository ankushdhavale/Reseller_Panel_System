package com.reseller.panel.dto;

import com.reseller.panel.entity.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponse {

    private Long id;
    private String username;
    private String token;
    private Role role;
    
    public LoginResponse(Long id,String username) {
    	this.username = username;
    	this.id = id;
    }
    
    public LoginResponse(Long id,String username,Role role) {
    	this.username = username;
    	this.id = id;
    	this.role = role;
    }
}


