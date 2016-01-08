package com.haalthy.service.cache;

import com.haalthy.service.common.SerializeUtil;
import org.apache.ibatis.cache.Cache;
import org.apache.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Ken on 2015/12/18.
 */
public final class MybatisRedisCache implements Cache {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    protected Logger logger = Logger.getLogger(this.getClass());
    private String id;

    private RedisCachePool cachePool = RedisCachePool.getInstance();

    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cache instances require an ID");
        }
        this.id = id;
    }

    private Object execute(RedisCallback callback) {
        Jedis jedis = cachePool.getJedis();
        boolean isBroken = false;
        Object result = null;
        try {
            result = callback.doWithRedis(jedis);
        } catch (Exception e) {
            logger.error(e);
            isBroken = true;
        } finally {
            cachePool.release(jedis, isBroken);
        }
        return result;
    }

    public String getId() {
        return this.id;
    }

    public int getSize() {
        return (Integer) execute(new RedisCallback() {
            public Object doWithRedis(Jedis jedis) {
                Map<byte[], byte[]> result = jedis.hgetAll(id.toString().getBytes());
                return result.size();
            }
        });
    }

    public void putObject(final Object key, final Object value) {
        execute(new RedisCallback() {
            public Object doWithRedis(Jedis jedis) {
                jedis.hset(id.toString().getBytes(), key.toString().getBytes(), SerializeUtil.serialize(value));
                return null;
            }
        });
    }

    public Object getObject(final Object key) {
        return execute(new RedisCallback() {
            public Object doWithRedis(Jedis jedis) {
                return SerializeUtil.unserialize(jedis.hget(id.toString().getBytes(), key.toString().getBytes()));
            }
        });
    }

    public Object removeObject(final Object key) {
        return execute(new RedisCallback() {
            public Object doWithRedis(Jedis jedis) {
                return jedis.hdel(id.toString(), key.toString());
            }
        });
    }

    public void clear() {
        execute(new RedisCallback() {
            public Object doWithRedis(Jedis jedis) {
                jedis.del(id.toString());
                return null;
            }
        });

    }

    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

    @Override
    public String toString() {
        return "Redis {" + id + "}";
    }

}
