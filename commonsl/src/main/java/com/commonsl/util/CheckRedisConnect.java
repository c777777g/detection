package com.commonsl.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;


public class CheckRedisConnect {


    @Autowired
    RedisMessageListenerContainer redisContainer;

    private Logger logger =Logger.getLogger(CheckRedisConnect.class);
    public void check(){
        String ping = "";

             ping =  redisContainer.getConnectionFactory().getConnection().ping();
            logger.info("ping是："+ping);

        if ( !ping.equals("PONG")){
            logger.info("quartz检测到一家断了000000000000000000000000000000000000000000000000000");
            redisContainer.stop();
            redisContainer.start();
        }
    }
    public RedisMessageListenerContainer getRedisContainer() {
        return redisContainer;
    }

    public void setRedisContainer(RedisMessageListenerContainer redisContainer) {
        this.redisContainer = redisContainer;
    }

} 