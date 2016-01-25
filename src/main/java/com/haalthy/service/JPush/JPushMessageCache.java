package com.haalthy.service.JPush;

import com.haalthy.service.common.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ken on 2016-01-08.
 */
@Service
public class JPushMessageCache {
    protected Logger logger=Logger.getLogger(this.getClass());

    @SuppressWarnings("rawtypes")
    @Resource
    private RedisTemplate redisTemplate;
    private static final String namespace="com.haalthy.service.JPush.msg.";

    @SuppressWarnings("unchecked")
    public void saveMessage(final JPushMessage msg)
    {
        logger.debug("putFieldValue to redis:to" +msg.getToUserName()+";from:"
                +msg.getPushMessageContent().getFromUserName()+";msg:" +msg.getPushMessageContent().getContent().toString());
        redisTemplate.execute(new RedisCallback<JPushMessage>(){
            private RedisConnection connection;

            @Override
            public JPushMessage doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] btyKey = redisTemplate.getStringSerializer().serialize(namespace + msg.getToUserName());
                byte[] btyField =  redisTemplate.getStringSerializer().serialize(StringUtils.DateToString(new Date(),"yyyyMMddHHmmssSSS"));
                byte[] btyBody = redisTemplate.getStringSerializer().serialize(StringUtils.getJson(msg.getPushMessageContent()));
                connection.hSetNX(btyKey,btyField,btyBody);
                connection.expire(btyKey,604800);
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Map<String,JPushMessageContent>getOfflineMessage(final String toUserName,final boolean bDeleteKey)
    {
        logger.debug("putFieldValue to redis:to" );
        Map<String,JPushMessageContent> map = new HashMap<String,JPushMessageContent>();
        Map<byte[],byte[]> keyValue = new HashMap<byte[],byte[]>();
        keyValue = (Map<byte[], byte[]>) redisTemplate.execute(new RedisCallback<Map<byte[],byte[]>>() {
            @Override
            public Map<byte[],byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
                Map<byte[],byte[]> keyValue2 = new HashMap<byte[],byte[]>();
                if(connection.exists(redisTemplate.getStringSerializer().serialize(namespace +toUserName)))
                {
                    keyValue2 =   connection.hGetAll(redisTemplate.getStringSerializer().serialize(namespace+"msg." +toUserName));
                }
                else
                    keyValue2 =  null;
                if(bDeleteKey)
                {
                    connection.del(redisTemplate.getStringSerializer().serialize(namespace+"msg." +toUserName));
                }
                return keyValue2;
            }
        });

        for (Map.Entry<byte[],byte[]> entry: keyValue.entrySet()
                ) {
            JPushMessageContent jpmc = (JPushMessageContent)StringUtils.getJava(entry.getValue().toString());
            map.put(entry.getKey().toString(),jpmc);
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    public boolean ExitsOfflineMessage(final String toUserName)
    {
        logger.debug("putFieldValue to redis:to" );
        return  (Boolean) redisTemplate.execute(new RedisCallback<Boolean>()
        {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                if(connection.exists(redisTemplate.getStringSerializer().serialize(namespace +toUserName)))
                {
                    return new Boolean(true);
                }
                else
                    return new Boolean(false);
            }
        });
    }
}
