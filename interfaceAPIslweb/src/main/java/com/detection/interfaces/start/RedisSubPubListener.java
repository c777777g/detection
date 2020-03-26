package com.detection.interfaces.start;

import org.apache.log4j.Logger;
import redis.clients.jedis.JedisPubSub;

public class RedisSubPubListener extends JedisPubSub {
    private Logger logger =Logger.getLogger(RedisSubPubListener.class);


    // 取得订阅的消息后的处理
    public void onMessage(String channel, String message) {
        logger.info("RedisSubPubListener收到：channel:"+channel+"message:"+message);
//        if(message.contains(RedisKey.HEARTBEAT.substring(0,6))) {
//            String deviceId = message.substring(RedisKey.HEARTBEAT.length() - 2, message.length());
//            Entity.DEVICECriteria deviceCriteria = new Entity.DEVICECriteria();
//            deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
//            DEVICE device = deviceService.selectOne(deviceCriteria);
//            device.setState("离线");
//            deviceService.update(device);
//
//            DEVICELOG devicelog = new DEVICELOG();
//            devicelog.setId(CommonUtil.createUserCode());
//            devicelog.setDeviceId(deviceId);
//            devicelog.setDate(new Date());
//            devicelog.setMessage("设备离线");
//            devicelogService.save(devicelog);
//
//            Date date = new Date();
//            logger.info("设备" + deviceId + "设备离线 " + date);
//           }
        }

    // 初始化订阅时候的处理
    public void onSubscribe(String channel, int subscribedChannels) {
        // System.out.println(channel + "=" + subscribedChannels);
    }

    // 取消订阅时候的处理
    public void onUnsubscribe(String channel, int subscribedChannels) {
        // System.out.println(channel + "=" + subscribedChannels);
    }

    // 初始化按表达式的方式订阅时候的处理
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取消按表达式的方式订阅时候的处理
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        // System.out.println(pattern + "=" + subscribedChannels);
    }

    // 取得按表达式的方式订阅的消息后的处理
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println(pattern + "=" + channel + "=" + message);
    }
}