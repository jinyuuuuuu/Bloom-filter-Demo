package com.scienjus.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;

import java.time.Duration;
import java.util.Objects;

@Configuration
public class RedisConfig extends CachingConfigurerSupport {

   /* *
     * 设置redis为默认的缓存工具
     * @param redisTemplate
     * @return*/


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory){
        RedisCacheWriter redisCacheWriter =  RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisSerializer<Object> jsonSerializer = new GenericToStringSerializer(Object.class);
        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer);

        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(pair);

        redisCacheConfiguration.entryTtl(Duration.ofSeconds(30));
        return  new RedisCacheManager(redisCacheWriter,redisCacheConfiguration);
    }
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return  redisTemplate;
    }
    @Bean
    public RedisTemplate<Long,String> redisTemplate1(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Long,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new GenericToStringSerializer<>(String.class));
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return  redisTemplate;
    }

}
