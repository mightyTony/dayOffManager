package mightytony.sideproject.dayoffmanager.config.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static mightytony.sideproject.dayoffmanager.common.Constants.REFRESH_TOKEN_EXPIRED_TIME;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Object> redisBlackListTemplate;

    public void saveRefreshToken(String refreshToken, String userId) {
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(userId.getClass()));
        //redisTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
        //redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMillis(REFRESH_TOKEN_EXPIRED_TIME));
        redisTemplate.opsForValue().set(refreshToken, userId, Duration.ofMinutes(1));
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

    public void addToBlackList(String key, Object o, int minutes) {
        redisBlackListTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(o.getClass()));
        redisBlackListTemplate.opsForValue().set(key, o, minutes, TimeUnit.MINUTES);
    }

    public Object getBlackList(String key) {
        return redisBlackListTemplate.opsForValue().get(key);
    }

    public boolean deleteBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.delete(key));
    }

    public boolean hasKeyBlackList(String key) {
        return Boolean.TRUE.equals(redisBlackListTemplate.hasKey(key));
    }
}
