package com.ex.cache.impl;

import com.ex.cache.VisitService;
import com.ex.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class VisitServiceImpl implements VisitService {
    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public Long getVisitCount() {
        return redisTemplate.opsForHyperLogLog().size(RedisConstant.VISIT_COUNT);
    }

    @Override
    public void incrementVisitCount(String...ips) {
        redisTemplate.opsForHyperLogLog().add(RedisConstant.VISIT_COUNT,ips);
    }
}
