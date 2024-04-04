package mightytony.sideproject.dayoffmanager.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void saveRefreshToken(String refreshToken, String userId) {
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(userId.getClass()));
        //redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
        //redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));
        redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));
    }


    public Object getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteRefreshToken(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    public void setTokenAddToBlackList(String key, Object o, long expirationRestTime) {
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        //redisTemplate.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
        redisTemplate.opsForValue().set(key, o, expirationRestTime);
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
        //redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(userId.getClass()));
        return value.equals(blackListValue);
    }


    public Object getBlackList(String key, String username) {
        return redisTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }
}
