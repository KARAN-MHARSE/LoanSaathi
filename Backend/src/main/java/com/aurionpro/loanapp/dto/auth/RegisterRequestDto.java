package com.aurionpro.loanapp.dto.auth;

import com.aurionpro.loanapp.property.RoleType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
	@NotBlank
	@Size(min = 3, max = 20, message = "First name must be valid")
	private String firstName;

	@NotBlank
	@Size(min = 3, max = 20, message = "Last name must be valid")
	private String lastName;

	@NotBlank
	@Email
	private String email;

	@NotBlank
	@Pattern(regexp = "^[6-9]\\d{9}$", message = "Please provide a valid 10-digit Indian mobile number.")
	private String phoneNumber;

	@NotBlank
	@Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters.")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.")
	private String password;
	
	@NotNull
	private RoleType roleName;
}
