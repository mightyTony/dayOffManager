package mightytony.sideproject.dayoffmanager.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories
public class RedisConfig {
    private final RedisProperties redisProperties;

    // RedisProperties 로 yml 에 저장한 host, port 연결
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
    }

    /**
     * 1. redisConnectionFactory(): LettuceConnectionFactory 객체 생성, 반환 메서드. 자바 lettuce 라이브러리 와 Redis 서버 간 연결
     * 2. redisTemplate() : RedisTemplate 객체 생성, 반환. RedisTemplate 은 Redis 데이터를 저장하고 조회하는 기능 하는 클래스
     * 3. setKeySerializer(), setValueSerializer() : Redis 데이터를 직렬화 하는 방식을 설정 할 수 있음. REdis CLI 를 사용해 Redis 데이터 직접 조회 시, REdis 데이터를 문자열로 반환해야하기 때문에 설정
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //redisTemplate.setValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }

    // CacheManager 빈 추가
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                //.entryTtl(Duration.ofHours(1)) // TTL 1 시간
                .entryTtl(Duration.ofMillis(1000))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration)
                .build();
    }
}
