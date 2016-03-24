package com.haalthy.service.JPush;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Ken on 2016-01-08.
 */
@Service("jPushRegister")
public class JPushRegister {

    protected Logger logger=Logger.getLogger(this.getClass());
    @SuppressWarnings("rawtypes")
    @Resource
    private RedisTemplate redisTemplate;
    private static final String namespace="com.haalthy.service.JPush.reg.";

    @SuppressWarnings("unchecked")
    public void Register(final String userName,final String jPushID)
    {
        redisTemplate.execute(new RedisCallback<String>(){
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + userName);
                byte[] btyField =  redisTemplate.getStringSerializer().serialize("pushID");
                byte[] btyBody = redisTemplate.getStringSerializer().serialize(jPushID);
                connection.hSet(btyKey,btyField,btyBody);
                connection.expire(btyKey,6048000);
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Object GetJPushID(final String userName)
    {
        return redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + userName);
                byte[] btyField =  redisTemplate.getStringSerializer().serialize("pushID");
                if(connection.hExists(btyKey,btyField))
                {
                    return redisTemplate.getStringSerializer().deserialize(connection.hGet(btyKey,btyField));
                }
                return null;
            }
        });
    }
}
