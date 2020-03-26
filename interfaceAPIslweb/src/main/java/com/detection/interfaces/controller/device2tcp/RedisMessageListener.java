package com.detection.interfaces.controller.device2tcp;


import com.commonsl.util.Redis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;


public class RedisMessageListener implements MessageListener {
    private Logger logger =Logger.getLogger(RedisMessageListener.class);


    @Autowired
    @Qualifier("pus")
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private Redis redis;

    @Override
    public void onMessage(Message message, byte[] pattern) {

        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        Object channel = serializer.deserialize(message.getChannel());
        Object body = serializer.deserialize(message.getBody());
        String m = String.valueOf(body);
            logger.info("onMessage 是"+channel+":"+m);

    }

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}