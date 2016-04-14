package com.haalthy.service.common;

import com.sun.deploy.util.SessionState;
import org.apache.log4j.Logger;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by Ken on 2015/12/18.
 */

public class ConfigLoader {

    private static Logger logger = Logger.getLogger(ConfigLoader.class);

    private static ConfigLoader configLoader=null;
    private Properties redisProps;
    private Properties configProps;

    private ConfigLoader(){
        try {
            redisProps = new Properties();
            redisProps.load(new InputStreamReader(SessionState.Client.class.getClassLoader().getResourceAsStream("redis.properties"), "UTF-8"));
            configProps = new Properties();
            configProps.load(new InputStreamReader(SessionState.Client.class.getClassLoader().getResourceAsStream("config.properties"), "UTF-8"));
            //PropertiesLoaderUtils.loadAllProperties("config.properties");
        } catch (IOException e) {
            // TODO 自动生成 catch 块
            logger.error("load chartServer Config failed!",e);
        }
    }

    public synchronized static ConfigLoader getInstance(){
        if(configLoader==null){
            configLoader=new ConfigLoader();
        }
        return configLoader;
    }


    public String getRedisProperty(String key){
        return this.redisProps.getProperty(key);
    }
    public String getConfigProperty(String key){
        return this.configProps.getProperty(key);
    }

}
