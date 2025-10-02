package com.aurionpro.loanapp.service;

public interface IRedisService {
	void set(String key,Object o, Long ttl);
	<T> T get(String key, Class<T> entityClass);
	

}
