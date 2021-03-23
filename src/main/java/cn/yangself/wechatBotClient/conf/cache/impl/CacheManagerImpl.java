package cn.yangself.wechatBotClient.conf.cache.impl;

import cn.yangself.wechatBotClient.conf.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class CacheManagerImpl implements CacheManager {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private final static StringRedisSerializer stringSerializer = new StringRedisSerializer();
    private final static GenericJackson2JsonRedisSerializer valueSerializer = new GenericJackson2JsonRedisSerializer();

    /**
     * 添加
     */
    public void putHashObject(String key, String hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key, hashKey, hashValue);
    }

    /**
     * 查询HashMap某个value
     */
    public Object getHashObject(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 查询HashMap的entry
     *
     * @param key 键
     */
    public Object getHashEntries(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 批量获取缓存中的数据
     */
    public List<Object> getMultiObject(String key, List<String> mapKeys) {
        if (StringUtils.isEmpty(key) || CollectionUtils.isEmpty(mapKeys)) {
            return Collections.emptyList();
        }

        return redisTemplate.opsForHash().multiGet(key, (Collection) mapKeys);
    }

    /**
     * 批量添加HashMap
     */
    public void putAll(String key, Map<Object, Object> objectMap) {
        if (objectMap == null || StringUtils.isEmpty(key) || objectMap.size() == 0) {
            return;
        }
        redisTemplate.opsForHash().putAll(key, objectMap);
    }

    public void delObject(String key, Object... params) {
        if (params == null || params.length == 0 || StringUtils.isEmpty(key)) {
            return;
        }
        redisTemplate.opsForHash().delete(key, params);
    }

    /**
     * 删除key
     */
    public void delKey(String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        redisTemplate.delete(key);
    }

    /**
     * 设置Key的过期时间
     *
     * @param key     KEY
     * @param timeOut 过期时间(秒)
     */
    public void expire(String key, int timeOut) {
        try {
            redisTemplate.expire(key, timeOut, TimeUnit.SECONDS);
        } catch (Exception e) {
            return;
        }
    }

    //-------------------6.2------------------------------

    /**
     * 添加
     */
    public Object getObjectValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 添加
     */
    public void putHashObject(String key, Object hashValue) {
        if (StringUtils.isEmpty(key) || hashValue == null) {
            return;
        }
        redisTemplate.opsForValue().set(key, hashValue);
    }

    /**
     * 批量获取缓存中的数据
     */
    public List<Object> getMultiObject(List<String> keys) {
        if (CollectionUtils.isEmpty(keys)) {
            return Collections.emptyList();
        }

        return redisTemplate.opsForValue().multiGet(keys);

    }

    /**
     * 批量设置缓存中的数据的失效时间
     */
    @Async
    public void expireMultiKey(List<String> keys, final int second) {
        final byte[][] rawHashes = new byte[keys.size()][];
        for (int counter = 0; counter < keys.size(); counter++) {
            rawHashes[counter] = this.rawKey(keys.get(counter));
        }

        redisTemplate.execute(new RedisCallback<List<Object>>() {
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (byte[] key : rawHashes) {
                    connection.expire(key, second);
                }
                return connection.closePipeline();
            }
        });
    }

    /**
     * 批量设置缓存中的数据
     */
    public void putMultiObject(Object[] keyValues) {

        final byte[][] rawHashes = new byte[keyValues.length][];
        Map<byte[], byte[]> keyValuesMap = new HashMap<>();

        String tempStr = "";
        for (int counter = 0; counter < keyValues.length; counter++) {

            if (counter % 2 == 0) {
                keyValuesMap.put(this.rawHashKey(tempStr), this.rawHashKey((String) keyValues[counter]));
            } else {
                tempStr = (String) keyValues[counter];
            }
        }
        redisTemplate.getConnectionFactory().getConnection().mSet(keyValuesMap);
    }

    /**
     * 删除keys
     */
    public void delMultiKey(List<String> keys) {
        final byte[][] rawHashes = new byte[keys.size()][];
        for (int counter = 0; counter < keys.size(); counter++) {
            rawHashes[counter] = this.rawKey(keys.get(counter));
        }
        redisTemplate.execute(new RedisCallback<List<Object>>() {
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                connection.openPipeline();
                for (byte[] key : rawHashes) {
                    connection.del(key);
                }
                return connection.closePipeline();
            }
        });
    }

    /**
     * 批量设置value
     */
    public void putMultiValue(Map<String, Object> map) {
        if (map == null || map.size() == 0) {
            return;
        }
        redisTemplate.opsForValue().multiSet(map);
    }

    @Override
    public void putObjectExpire(String key, Object value, long timeout, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(key) || Objects.isNull(value)) {
            return;
        }
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Object object = redisTemplate.opsForValue().get(key);
        if (Objects.nonNull(object)) {
            return clazz.cast(object);
        }
        return null;
    }

    byte[] rawKey(String key) {
        return this.stringSerializer(key);
    }

    byte[] rawHashKey(String key) {
        return this.stringSerializer(key);
    }

    byte[] rawHashValue(Object value) {
        return this.hashValueSerializer(value);
    }

    byte[] stringSerializer(String str) {
        return stringSerializer.serialize(str);
    }

    byte[] hashValueSerializer(Object s) {
        return valueSerializer.serialize(s);
    }
}
