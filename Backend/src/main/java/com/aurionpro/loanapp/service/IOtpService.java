package com.aurionpro.loanapp.service;

public interface IOtpService {
	String generateOtp(String email,long ttl);
	boolean validateOtp(String email,String otp);
}
