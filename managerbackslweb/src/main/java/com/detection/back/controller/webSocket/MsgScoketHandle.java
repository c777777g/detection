package com.detection.back.controller.webSocket;

import com.commonsl.json.JsonSensorAlarm;
import com.commonsl.model.*;
import com.commonsl.service.DeviceSensorService;
import com.commonsl.service.DeviceService;
import com.commonsl.service.LogService;
import com.commonsl.service.UserService;
import com.commonsl.util.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.commonsl.util.JsonHelper.toJson;


public class MsgScoketHandle implements WebSocketHandler {
    private Logger logger = Logger.getLogger(WebSocketHandler.class);
    // 已经连接的用户
    private static final ConcurrentHashMap<String, WebSocketUser> webSocketSessionUserArrayList = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, WebSocketDevice> webSocketDeviceConcurrentHashMap = new ConcurrentHashMap<>();

    private static Timer timerUpdateDeviceSensor = new Timer();
    private static Timer timerDeviceSensorAlarm = new Timer();

    @Autowired
    private DeviceSensorService deviceSensorService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private Redis redis;

    @Autowired
    private LogService logService;

    public MsgScoketHandle() {  //部署服务器是记得开检测业务
        stratTimerUpdateDeviceSensor();
        stratTimerDeviceSensorAlarm();
    }

    /**
     * 建立链接
     *
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        //将用户信息添加到list中

        System.out.println("=====================建立连接成功==========================");
        logger.info("webSocket连接数量=====" + webSocketSessionUserArrayList.size());
    }

    /**
     * 接收消息
     *
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

        String src = webSocketMessage.getPayload().toString();
        logger.info(src);
        logger.info("===========================================");

        JSONObject jsonObject = JSONObject.fromObject(src);
        String type = jsonObject.getString("type");
        String id = jsonObject.getString("id");
        if (type == null || id == null) {
            logger.info("登陆消息失败：" + src);
            return;
        }

        switch (jsonObject.getString("type")) {
            case "user":
                logger.info("添加用户");
                Entity.UserCriteria userCriteria = new Entity.UserCriteria();
                userCriteria.setUserName(Entity.Value.eq(id));
                User user = userService.selectOne(userCriteria);
                webSocketSessionUserArrayList.put(webSocketSession.getId(), new WebSocketUser(user, webSocketSession));
                logger.info("添加用户成功");
                break;
            case "device":
                logger.info("添加设备");
                webSocketDeviceConcurrentHashMap.put(webSocketSession.getId(), new WebSocketDevice(id, webSocketSession));
                logger.info("添加设备成功");
                break;
        }
//        webSocketSession.sendMessage(new TextMessage(src));
    }

    /**
     * 异常处理
     *
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) {
        logger.info("异常断开");
        if (webSocketSession.isOpen()) {
            //关闭session
            try {
                webSocketSession.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //移除用户
//        webSocketSessionUserArrayList.remove(webSocketSession);
//        webSocketDeviceConcurrentHashMap.remove(webSocketSession.getId());
    }

    /**
     * 断开链接
     *
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus
            closeStatus) throws Exception {
        logger.info("离线断开");
        webSocketDeviceConcurrentHashMap.remove(webSocketSession.getId());
        WebSocketUser webSocketUser = webSocketSessionUserArrayList.remove(webSocketSession.getId());
        if (webSocketUser != null) {
            User user = webSocketUser.getUser();
            user.setLandingTime(new Date());
            user.setState("离线");
            userService.update(user);
        }
//        User user = (User) webSocketSession.getAttributes().get("user");
//        System.out.println(user.getUserName() + "断开连接");

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 发送消息给指定的用户
     *
     * @param user
     * @param message
     */
    public void sendMessageToUser(User user, String message) {
        for (WebSocketUser webSocketUser : webSocketSessionUserArrayList.values()) {
            try {
                if (webSocketUser.getWebSocketSession().isOpen() && webSocketUser.getUser().getUserName().equals(user.getUserName())) {
                    webSocketUser.sendMessage(message);
                    logger.info("发送消息给：" + user.getUserName() + "内容：" + message);
                }
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void massSend(String message) {
        for (WebSocketUser webSocketUser : webSocketSessionUserArrayList.values()) {
            try {
                webSocketUser.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }




    public void stratTimerUpdateDeviceSensor() {
        timerUpdateDeviceSensor.schedule(new UpdataSensorEvent(), 500, 2000);//tiemr.schedule(执行的方法,延迟时间,多久执行一次
        logger.info("开启更新设备状态");
    }

    public void stratTimerDeviceSensorAlarm() {
        timerDeviceSensorAlarm.schedule(new getSensorAlarmEvent(), 500, 2000);//tiemr.schedule(执行的方法,延迟时间,多久执行一次
        logger.info("开启获取上报报警事件");
    }



    class UpdataSensorEvent extends TimerTask {
        @Override
        public void run() {

            for (WebSocketDevice webSocketDevice : webSocketDeviceConcurrentHashMap.values()) {
                try {
                    Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
                    deviceSensorCriteria.setDeviceId(Entity.Value.eq(webSocketDevice.getDeviceId()));
                    DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);

                    Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
                    deviceCriteria.setDeviceId(Entity.Value.eq(webSocketDevice.getDeviceId()));
                    Device device = deviceService.selectOne(deviceCriteria);


                    if (deviceSensor != null) {
                        webSocketDevice.sendDeviceSensor(device, deviceSensor);
                    } else {
                        logger.info("deviceSensor为空");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class getSensorAlarmEvent extends TimerTask {
        @Override
        public void run() {
            Set<String> keys = redis.getKeys(RedisKey.SENSORALARM.substring(0, RedisKey.SENSORALARM.length() - 2) + "*");
            for (String key : keys) {
                try {
                    String j = redis.getString(key);
                    JsonSensorAlarm jsonSensorAlarm = JsonHelper.fromJson(j, JsonSensorAlarm.class);

                    Map<String, Object> map = new HashMap<>();
                    String back = PropertyUtil.getProperty("spot.back");
                    String httpPort = PropertyUtil.getProperty("spot.httpPort");
                    NetworkUtil networkUtil = new NetworkUtil();


                    map.put("url", "http://" + networkUtil.getIpAdd() + ":" + httpPort + back + "/deviceMgt/show.do?deviceId=" + jsonSensorAlarm.getDeviceId());
                    map.put("event", jsonSensorAlarm);
//                    map.put("s","监控："+event.getMonitorId()+"_"+event.getName()+",原因 :"+event.getCause()+",时间："+event.getStartTime());
                    map.put("type", 2);
                    String m = toJson(map);
                    logger.info("发送上报数据报警：" + m);

                    Log log = new Log();
                    log.setDeviceId(jsonSensorAlarm.getDeviceId());
                    log.setDeviceName(jsonSensorAlarm.getDeviceName());
                    log.setType(2);
                    log.setCreateTime(new Date());

                    log.setMessage(jsonSensorAlarm.getAlarmMessage());
                    logService.save(log);

                    massSend(m);
                    redis.del(key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
