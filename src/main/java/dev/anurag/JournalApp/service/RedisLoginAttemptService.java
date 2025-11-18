package dev.anurag.JournalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisLoginAttemptService {

    private static final String ATTEMPT_PREFIX = "login_attempts : ";

    @Autowired
    private StringRedisTemplate redisTemplate;

    public int getAttempts(String username){
        String key = ATTEMPT_PREFIX + username;
        String value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : Integer.parseInt(value);
    }

    public void incrementAttempts(String username){
        String key = ATTEMPT_PREFIX + username;
        int attempts = getAttempts(username) + 1;
        redisTemplate.opsForValue().set(key, String.valueOf(attempts), 15, TimeUnit.MINUTES);
    }

    public void resetAttempts(String username){
        redisTemplate.delete(ATTEMPT_PREFIX + username);
    }



}
