package com.reseller.panel.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reseller.panel.entity.User;
import com.reseller.panel.entity.enums.Role;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

    List<User> findByRole(Role role); 
}
