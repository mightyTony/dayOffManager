package mightytony.sideproject.dayoffmanager.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshToken(String refreshToken, String userId) {
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(userId.getClass()));
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(refreshToken.getClass()));
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(refreshToken.getClass()));
        //redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));
        redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));
    }


//    public Object getRefreshToken(String key) {
//        return redisTemplate.opsForValue().get(key);
//    }

//    public boolean deleteRefreshToken(String key) {
//        return Boolean.TRUE.equals(redisTemplate.delete(key));
//    }

//    public boolean hasKey(String key) {
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }

    public void setAccessTokenAddToBlackList(String token, String username) {
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(token.getClass()));
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(token.getClass()));

        log.info("=============Username length : {}", username.length());
        log.info("=============Key length : {}",token.length());
        redisTemplate.opsForValue().set(token, username, Duration.ofHours(1));
    }

    public void setRefreshTokenAddToBlackList(String token, String username) {
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(token.getClass()));
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<>(token.getClass()));

        log.info("=============Username length : {}", username.length());
        log.info("=============Key length : {}",token.length());
        redisTemplate.opsForValue().set(token, username, Duration.ofDays(14));
    }

//    public void accessTokenAddToBlackList(String key, Object o, long expirationRestTime) {
//        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
//        redisBlackListTemplate.opsForValue().set(key, o, Duration.ofMillis(expirationRestTime));
//    }

    public boolean isTokenInBlackList(String token, String userId) {
        String value = (String) redisTemplate.opsForValue().get(token);
        if (value == null) {
            return false; // 토큰에 해당하는 값이 없으면 블랙리스트에 없다고 판단
        }
        String blackListValue = "BL:" + userId;
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(token.getClass()));
        return value.equals(blackListValue);
    }


//    public Object getBlackList(String key, String username) {
//        return redisTemplate.opsForValue().get(key);
//    }

//    public boolean deleteBlackList(String key) {
//        return Boolean.TRUE.equals(redisTemplate.delete(key));
//    }

//    public boolean hasKeyBlackList(String key) {
//        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
//    }
}
