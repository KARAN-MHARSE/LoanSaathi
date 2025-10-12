package com.aurionpro.loanapp.service.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.service.IOtpService;
import com.aurionpro.loanapp.service.IRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpService implements IOtpService {
	private final IRedisService redisService;

	@Override
	public String generateOtp(String email, long ttl) {
		String otp = String.valueOf((int)(Math.random()*900000)+100000);
		redisService.set(email, otp, 120L);
		return otp;
	}

	@Override
	public boolean validateOtp(String email, String otp) {
		String storedOtp = redisService.get(email, String.class);
		System.out.println("Otp here"+otp);
		System.out.println(storedOtp);
		return otp.equals(storedOtp);
	}

}
