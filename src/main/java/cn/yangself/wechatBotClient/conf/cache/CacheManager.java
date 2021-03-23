package cn.yangself.wechatBotClient.conf.cache;

import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public interface CacheManager {

    /**
     * 添加
     */
    void putHashObject(String key, String hashKey, Object hashValue);

    /**
     * 查询HashMap某个value
     */
    Object getHashObject(String key, String hashKey);

    /**
     * 查询HashMap的entry
     *
     * @param key 键
     */
    Object getHashEntries(String key);

    /**
     * 批量获取缓存中的数据
     */
    List<Object> getMultiObject(String key, List<String> mapKeys);

    /**
     * 批量添加HashMap
     */
    void putAll(String key, Map<Object, Object> objectMap);

    /**
     * 批量删除
     */
    void delObject(String key, Object... params);

    /**
     * 删除key
     */
    void delKey(String key);

    /**
     * 设置Key的过期时间
     *
     * @param key     KEY
     * @param timeOut 过期时间(秒)
     */
    void expire(String key, int timeOut);

    //add

    /**
     * 添加
     */
    Object getObjectValue(String key);

    /**
     * 添加
     */
    void putHashObject(String key, Object hashValue);

    /**
     * 批量获取缓存中的数据
     */
    List<Object> getMultiObject(List<String> keys);
    //

    /**
     * 批量设置缓存中的数据
     *
     * @param keyValues
     * @return
     */
    void putMultiObject(Object[] keyValues);

    /**
     * 批量设置缓存中的数据的失效时间
     *
     * @param timeOut 过期时间(秒)
     */
    @Async
    void expireMultiKey(List<String> keys, int timeOut);

    /**
     * 批量删除keys
     */
    void delMultiKey(List<String> keys);

    /**
     * 批量设置value
     */
    @Async
    void putMultiValue(Map<String, Object> map);

    /**
     * 设置key-value-过期时间
     *
     * @param key
     * @param value
     * @param timeout
     * @param timeUnit
     */
    void putObjectExpire(String key, Object value, long timeout, TimeUnit timeUnit);

    /**
     * 根据key获取对象
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getObject(String key, Class<T> clazz);
}