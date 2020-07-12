package com.anycommon.cache.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.StringJoiner;

/**
 * @author wangkai
 */
@Configuration
@ConditionalOnClass(RedisOperations.class)
@EnableConfigurationProperties(RedisProperties.class)
@ConditionalOnProperty(prefix = "platform.redis", name = "type",havingValue = "standalone")
@PropertySource(value = "classpath:redis-${spring.profiles.active}-${platform.redis}.properties")
public class RedisStandAloneConfig extends CachingConfigurerSupport {

    /**
     * RedisTemplate配置
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(keySerializer());
        redisTemplate.setHashKeySerializer(keySerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.setHashValueSerializer(valueSerializer());
        return redisTemplate;
    }


    /**
     * 管理缓存
     */
    @Bean
    @Primary
    public CacheManager cacheManager(LettuceConnectionFactory factory){
        //缓存配置对象
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                //设置缓存的默认超时时间：30分钟
                .entryTtl(Duration.ofMinutes(30L))
                //如果是空值，不缓存
                .disableCachingNullValues()
                //设置key序列化器
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
                //设置value序列化器
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer((valueSerializer())));

        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
                .cacheDefaults(cacheConfig).build();
    }
    /**
     * key序列化
     */
    private RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }
    /**
     * value序列化
     */
    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }
}
