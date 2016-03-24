package com.haalthy.service.cache;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Ken on 2016-01-13.
 */

@Service("redisMapCache")
public class RedisMapCache {
    protected Logger logger=Logger.getLogger(this.getClass());

    @SuppressWarnings("rawtypes")
    @Resource
    private RedisTemplate redisTemplate;
    private static final String namespace="haalthy.map.";


    @SuppressWarnings("unchecked")
    public void putValue(final String key,final String field,final String value,final long expire)
    {
        logger.info("putObject to redis data:" + key+field+value);
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                byte[] btyField = redisTemplate.getStringSerializer().serialize(field);
                byte[] btyValue = redisTemplate.getStringSerializer().serialize(value);
                connection.hSet(btyKey,btyField,btyValue);
                if(expire > 0)
                    connection.expire(btyKey,expire);
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void setValue(final String key,final String field,final String value,final long expire)
    {
        logger.info("putObject to redis data:" + key+field+value);
        redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                byte[] btyField = redisTemplate.getStringSerializer().serialize(field);
                byte[] btyValue = redisTemplate.getStringSerializer().serialize(value);
                if(connection.hExists(btyKey,btyField))
                    connection.hSet(btyKey,btyField,btyValue);
                if(expire > 0)
                    connection.expire(btyKey,expire);
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public boolean Exists(final String key,final String field)
    {
        return (Boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                byte[] btyField = redisTemplate.getStringSerializer().serialize(field);
                return connection.hExists(btyKey,btyField);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public String getValue(final String key,final String field)
    {
        logger.info("putObject to redis data:" + key+field);
        return (String)redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                byte[] btyField = redisTemplate.getStringSerializer().serialize(field);
                return redisTemplate.getStringSerializer().deserialize(connection.hGet(btyKey,btyField)).toString();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Map<String,String>getValues(final String key,final List<String> fields)
    {
        Map<String,String> map = new HashMap<String,String>();
        for (String s: fields
             ) {
            map.put(s,getValue(key,s));
        }
        return map;
    }


    @SuppressWarnings("unchecked")
    public Map<String,String>getAllValues(final String key)
    {
        Map<byte[],byte[]> keyValue
                = (Map<byte[], byte[]>) redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
            @Override
            public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hGetAll(redisTemplate.getStringSerializer().serialize(namespace+key));
            }
        });
        Map<String,String> map = new HashMap<String,String>();
        for (Map.Entry<byte[],byte[]> e:keyValue.entrySet()
             ) {
            map.put(redisTemplate.getStringSerializer().deserialize(e.getKey()).toString(),
                    redisTemplate.getStringSerializer().deserialize(e.getValue()).toString());
        }
        return map;
    }

    /*
    * 获取value大于等于制定值的字段
    *
    * */
    @SuppressWarnings("unchecked")
    public Map<String,String>getValuesUp(final String key,final String value)
    {
        Map<byte[],byte[]> keyValue
                = (Map<byte[], byte[]>) redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
            @Override
            public Map<byte[], byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hGetAll(redisTemplate.getStringSerializer().serialize(namespace+key));
            }
        });
        Map<String,String> map = new HashMap<String,String>();
        for (Map.Entry<byte[],byte[]> e:keyValue.entrySet()
                ) {
            if( redisTemplate.getStringSerializer().deserialize(e.getValue()).toString().compareTo(value)>=0)
                map.put(redisTemplate.getStringSerializer().deserialize(e.getKey()).toString(),
                        redisTemplate.getStringSerializer().deserialize(e.getValue()).toString());
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public List<String>getAllFields(final String key)
    {
        Set<byte[]> fields = (Set<byte[]>)redisTemplate.execute(new RedisCallback<Set<byte[]>>(){
                    @Override
                    public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                        byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);

                        return connection.hKeys(btyKey);
                    }
                });
        List <String> listFields = new ArrayList<String>();
        for (byte[] s:fields
             ) {
            listFields.add(redisTemplate.getStringSerializer().deserialize(s).toString());
        }
        return listFields;
    }

    @SuppressWarnings("unchecked")
    public void delField(final String key,final String field)
    {
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);

                connection.hDel(btyKey,redisTemplate.getStringSerializer().serialize(field));
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void delFields(final String key,final List<String> fields)
    {
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);

                for (String s:fields
                        ) {
                    connection.hDel(btyKey,redisTemplate.getStringSerializer().serialize(s));
                }
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public void delKey(final String key)
    {
        redisTemplate.execute(new RedisCallback<Object>(){
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                connection.del(btyKey);
                return null;
            }
        });
    }

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
                return null;
            }
        });
    }


    @SuppressWarnings("unchecked")
    public long getLength(final String key)
    {
        logger.info("getLength from redis data:" + key);
        return (Long)redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + key);
                return connection.hLen(btyKey);
            }
        });
    }
}
