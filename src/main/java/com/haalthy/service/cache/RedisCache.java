package com.haalthy.service.cache;

/**
 * Created by Ken on 2015/12/18.
 */

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("redisCache")
public class RedisCache
{
    protected Logger logger=Logger.getLogger(this.getClass());

    @SuppressWarnings("rawtypes")
    @Resource
    private RedisTemplate redisTemplate;
    private static final String namespace="com.haalthy.service.";

    @SuppressWarnings("unchecked")
    public void putObject(final String key, final Object obj){
        logger.info("putObject to redis data:" + obj.toString());
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
                map.put(redisTemplate.getStringSerializer().serialize("obj"), redisTemplate.getDefaultSerializer().serialize(obj));
                connection.hMSet(btyKey, map);
                return null;

            }
        });
    }

    @SuppressWarnings("unchecked")
    public Object getObject(final String key){
        logger.info("getObject from redis key:" + key);
        return redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                if (connection.exists(btyKey)){
                    List<byte[]> value = connection.hMGet(btyKey, redisTemplate.getStringSerializer().serialize("obj"));
                    return redisTemplate.getDefaultSerializer().deserialize(value.get(0));
                }
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void deleteObject(final String key){
        logger.info("deleteObject from redis key:" + key);
        redisTemplate.execute(new RedisCallback<Object>(){
            public Object doInRedis(RedisConnection connection){
                connection.del(redisTemplate.getStringSerializer().serialize(namespace + key));
                return null;
            }
        });
    }

    /**
     * 指定过期时间，黙认保存7天
     */
    @SuppressWarnings("unchecked")
    public void putObject(final String key, final Object body, final long seconds) {
        // TODO Auto-generated method stub
        logger.info("putObject to redis data:" + body.toString()+"; data expire:"+seconds);
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                Map<byte[], byte[]> map = new HashMap<byte[], byte[]>();
                map.put(redisTemplate.getStringSerializer().serialize("obj"), redisTemplate.getDefaultSerializer().serialize(body));
                connection.hMSet(btyKey, map);
                if(seconds>0)
                    connection.expire(btyKey, seconds);
                else
                    connection.expire(btyKey, 604800);
                return null;
            }
        });
    }

    /* (non-Javadoc)
     * @see com.sunray.soscloud.base.cache.RedisCache#resetExpire(java.lang.String, long)
     */
    @SuppressWarnings("unchecked")
    public void resetExpire(final String key, final long seconds) {
        // TODO Auto-generated method stub
        logger.info("resetExpire to redis key:" + key+"; expire:"+seconds);
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                if(seconds>0)
                    connection.expire(btyKey, seconds);
                else
                    connection.expire(btyKey, 604800);
                return null;
            }
        });
    }
}
