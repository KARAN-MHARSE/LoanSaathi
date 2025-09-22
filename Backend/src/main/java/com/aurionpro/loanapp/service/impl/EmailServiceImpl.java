package com.aurionpro.loanapp.service.impl;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.dto.EmailDto;
import com.aurionpro.loanapp.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
	private final JavaMailSender javaMailSender;
	
	@Override
	public void sendEmailWithImage(EmailDto emailDto) throws MessagingException{
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		
		helper.setTo(emailDto.getTo());
		helper.setSubject(emailDto.getSubject());
//		helper.setText(emailDto.getBody());
		
		String htmlContent = "<p>"+emailDto.getBody() +"</p>"+
							"<img src='" + emailDto.getImage()+"' alt='image not found'/>";
		helper.setText(htmlContent,true);
		
		javaMailSender.send(mimeMessage);
		
		
	}

	@Override
	public void sendEmailWithoutImage(EmailDto emailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(emailDto.getTo());
		message.setSubject(emailDto.getSubject());
		message.setText(emailDto.getBody());
		
		javaMailSender.send(message);

		
	}
	

}
