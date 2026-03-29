package com.reseller.panel.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reseller.panel.dto.LoginResponse;
import com.reseller.panel.dto.UserRequest;
import com.reseller.panel.dto.UserResponse;
import com.reseller.panel.entity.User;
import com.reseller.panel.entity.Wallet;
import com.reseller.panel.entity.enums.Role;
import com.reseller.panel.exception.BadRequestException;
import com.reseller.panel.exception.DuplicateResourceException;
import com.reseller.panel.exception.ResourceNotFoundException;
import com.reseller.panel.exception.UnauthorizedException;
import com.reseller.panel.repository.UserRepository;
import com.reseller.panel.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;
    private final WalletRepository walletRepo;
    private final PasswordEncoder passwordEncoder;


    public UserResponse createUser(UserRequest request) {
        if (request.getUsername() == null || request.getPassword() == null)
            throw new BadRequestException("Username and password are required");

        if (userRepo.findByUsername(request.getUsername()).isPresent())
            throw new DuplicateResourceException("Username already exists");

        Role role;
        try {
            role = Role.valueOf(request.getRole().toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Invalid role. Use ADMIN or RESELLER");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .createdAt(LocalDateTime.now())
                .build();

        user = userRepo.save(user);

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(0.0)
                .creatAt(LocalDateTime.now())
                .build();

        walletRepo.save(wallet);

        return mapToResponse(user);
    }

   
    public LoginResponse login(String username, String password) {
        if (username == null || password == null)
            throw new BadRequestException("Username and password are required");

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UnauthorizedException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new UnauthorizedException("Invalid username or password");

        return new LoginResponse(user.getId(), user.getUsername(), user.getRole());
    }

    public LoginResponse getUserByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return new LoginResponse(user.getId(), user.getUsername(), user.getRole());
    }

    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return mapToResponse(user);
    }

    private UserResponse mapToResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole().name(), user.getCreatedAt());
    }
}