package com.haalthy.service.cache;

/**
 * Created by Ken on 2015/12/18.
 */
public interface RedisCache {

    /**
     * 向redis里存入对象
     * @param sessionid
     * @param bf
     */
    void putObject(final String key, final Object body);

    /**
     * 向redis里存入对象,并指定过期时间
     * @param key
     * @param body
     * @param seconds
     */
    void putObject(final String key, final Object body,final long seconds);

    /**
     * 从redis里取出对象
     * @param sessionid
     * @return
     */
    Object getObject(final String key);

    /**
     * 删除redis里的对象
     * @param sessionId
     */
    void deleteObject(final String key);


    /**
     * 重设key的过期时间
     * @param key
     * @param seconds
     */
    void resetExpire(final String key,final long seconds);
}
