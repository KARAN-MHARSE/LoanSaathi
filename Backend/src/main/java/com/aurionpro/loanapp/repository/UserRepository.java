package com.aurionpro.loanapp.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Role;
import com.aurionpro.loanapp.entity.User;
import com.aurionpro.loanapp.property.RoleType;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
    Page<User> findAllByRole(RoleType role, Pageable pageable);

}
