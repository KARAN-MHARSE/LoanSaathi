package com.aurionpro.loanapp.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@ManyToOne
	@JoinColumn(name="roleId")
	private Role role;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return List.of((GrantedAuthority) () -> role.getRoleName().name());

	}

}
