package com.aurionpro.loanapp.dto;

import jakarta.mail.Multipart;
import lombok.Data;

@Data
public class EmailDto {
	private String to;
	private String subject;
	private String body;
	private String image;
}
