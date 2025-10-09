package com.aurionpro.loanapp.dto.officer;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OfficerRequestDto {

    @NotBlank(message = "Email is mandatory")
    private Long id;

    @NotNull(message = "Hire date is required")
    private LocalDate hireDate;
}
