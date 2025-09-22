package com.aurionpro.loanapp.service;

import com.aurionpro.loanapp.dto.EmailDto;

import jakarta.mail.MessagingException;

public interface EmailService {
	void sendEmailWithImage(EmailDto emailDto)  throws MessagingException ;
	void sendEmailWithoutImage(EmailDto emailDto);
}
