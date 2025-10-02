package com.aurionpro.loanapp.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aurionpro.loanapp.service.IRedisService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisService implements IRedisService{
	private final RedisTemplate redisTemplate;

	@Override
	public void set(String key, Object o, Long ttl) {
		redisTemplate.opsForValue().set(key, o,ttl,TimeUnit.SECONDS);
	}

	@Override
	public <T> T get(String key, Class<T> entityClass) {
		Object object = redisTemplate.opsForValue().get("civib58005@gddcorp.com");
		System.out.println("From redisService"+object.toString());
		if(object==null) return null;
		return entityClass.cast(object);
	}

}
