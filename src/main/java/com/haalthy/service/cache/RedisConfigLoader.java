package com.haalthy.service.cache;

import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Ken on 2015/12/18.
 */

public class RedisConfigLoader {

    private static Logger logger = Logger.getLogger(RedisConfigLoader.class);

    private static RedisConfigLoader configLoader=null;
    private Properties redisProps;

    private RedisConfigLoader(){
        try {
            redisProps = PropertiesLoaderUtils.loadAllProperties("redis.properties");
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            logger.error("load chartServer Config failed!",e);
        }
    }

    public synchronized static RedisConfigLoader getInstance(){
        if(configLoader==null){
            configLoader=new RedisConfigLoader();
        }
        return configLoader;
    }


    public String getRedisProperty(String key){
        return this.redisProps.getProperty(key);
    }

}
