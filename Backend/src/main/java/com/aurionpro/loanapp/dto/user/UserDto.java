package com.aurionpro.loanapp.dto;
import com.aurionpro.loanapp.property.RoleType;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private RoleType roleName;
}