package com.aurionpro.loanapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurionpro.loanapp.entity.Role;
import com.aurionpro.loanapp.property.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long>{
	Optional<Role> findByRoleName(RoleType roleName);
	public boolean existsByRoleName(RoleType roleName);
}

