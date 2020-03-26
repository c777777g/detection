package com.detection.interfaces.controller.mina;

import com.commonsl.json.*;
import com.commonsl.util.JsonHelper;
import com.commonsl.util.Redis;
import com.detection.interfaces.controller.device2tcp.DeviceRegistry;
import com.detection.interfaces.controller.device2tcp.DeviceSession;
import com.detection.interfaces.controller.user2tcp.UserRegistry;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.net.InetSocketAddress;

import static com.commonsl.util.JsonHelper.fromJson;
import static com.commonsl.util.JsonHelper.toJson;

public class ServerHandler extends IoHandlerAdapter implements MessageListener {
    private Logger log = Logger.getLogger(ServerHandler.class);
    @Autowired
    private Redis redis;

    @Autowired
    private static DeviceRegistry deviceRegistry;

    @Autowired
    private static UserRegistry userRegistry;



    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setDeviceRegistry(DeviceRegistry deviceRegistry) {
        this.deviceRegistry = deviceRegistry;
    }

    public void setUserRegistry(UserRegistry userRegistry) {
        this.userRegistry = userRegistry;
    }


    @Autowired
    @Qualifier("pus")
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public synchronized void onMessage(Message message, byte[] pattern) {
        RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
        Object channel = serializer.deserialize(message.getChannel());
        Object body = serializer.deserialize(message.getBody());
        String m = String.valueOf(body);

        switch (channel.toString()) {
            case "control":
                log.info("redis 收到的频道" + channel + ":" + m);
                JsonControl jsonControl = fromJson(m, JsonControl.class);
                DeviceSession deviceSession = deviceRegistry.getById(jsonControl.getDeviceId());
                if (deviceSession != null) {
                    deviceSession.sendMessage(m);
                }
                break;
            case "setDevice":
                log.info("redis 收的频道" + channel + ":" + m);
               // JsonSetDevice jsonSetDevice = fromJsn(m, JsonSetDevice.class);
                JSONObject jsonObject = JSONObject.fromObject(m);
                DeviceSession deviceSession1 = deviceRegistry.getById(jsonObject.getString("deviceId"));
                if (deviceSession1 != null) {
                    deviceSession1.sendMessage(m);
                }
                break;



            case "warningNotify":
                log.info("redis 收到的频道" + channel + ":" + m);
                userRegistry.massSend(m);
                break;
        }
    }


    @Override
    public void sessionCreated(IoSession session) throws Exception {
        InetSocketAddress adress = (InetSocketAddress) session.getRemoteAddress();
        log.info("hostString:" + adress.getHostString());
        log.info("hostName:" + adress.getHostName());
        log.info("address:" + adress.getAddress());
        log.info("port:" + adress.getPort());
    }

    @Override
    public synchronized void messageReceived(IoSession session, Object message) throws Exception {
        String str = message.toString();
        log.info(session.getId()+": he message received is [" + str + "]");
        JsonLogin jsonLogin = fromJson(str, JsonLogin.class);
        if (jsonLogin == null) {
            return;
        }
        log.info("jsonLogin.getOpcode():" + jsonLogin.getOpcode());
        switch (jsonLogin.getOpcode()) {
            //设备接口
            case "login":
                login(jsonLogin, session);
                break;
            case "reportedData":
                if (checklogin(session)) {
                    reportedData(str, session);
                }
                break;

            case "controlOpenAnswer":

                break;



            //app接口
            case "userLogin":
                userlogin(str, session);
                break;
            case "devicePage":
                if (checkUserlogin(session)) {
                    getDevicePage(str, session);
                }
                break;

            case "deviceSenator":
                if (checkUserlogin(session)) {
                    getDeviceSenator(str, session);
                }
                break;
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // Empty handler
        log.info("关闭：" + session.getId());

        DeviceSession deviceSession = deviceRegistry.removeBySession(session);
        if (deviceSession != null) {
            //通知用户设备实时状态
            userRegistry.SendDeviceSenatorToAll(deviceSession.getDeviceId());
        }
        //如果是用户离线
        userRegistry.removeBySession(session);

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
//       session.closeNow();
        log.info("冷静：" + session.getId());
    }

    /**
     * 检测是否已经登陆
     *
     * @param session
     */
    public boolean checklogin(IoSession session) {
        if (!deviceRegistry.existsBySessionId(session.getId() + "")) {
            JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.not_login);
            session.write(toJson(jsonAnswer));
            session.closeNow();
            return false;
        }
        return true;
    }


    /**
     * 登陆
     *
     * @param object
     * @param session
     */
    public synchronized void login(Object object, IoSession session) {

        JsonLogin jsonLogin = (JsonLogin) object;
        log.info("设备登陆：" + JsonHelper.toJson(jsonLogin));
        if (jsonLogin == null || jsonLogin.getDeviceId() == null) {
            JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.protocol_error);
            session.write(toJson(jsonAnswer));
            session.closeNow();
        }
        deviceRegistry.register(jsonLogin.getDeviceId(), session);
        userRegistry.SendDeviceSenatorToAll(jsonLogin.getDeviceId());
    }

    public void reportedData(String str, IoSession session) {
        JsonReportedData jsonReportedData = fromJson(str, JsonReportedData.class);
        DeviceSession deviceSession = deviceRegistry.getBySession(session);
        if (jsonReportedData != null && deviceSession != null) {
            JsonSensorAlarm jsonSensorAlarm = deviceRegistry.reportedData(jsonReportedData, deviceSession);

            //设备更新信息向app发送
//            userRegistry.SendDeviceSenatorToAll(deviceSession.getDeviceId());
            if (jsonSensorAlarm != null) {
                //警报信息
//                userRegistry.massSend(JsonHelper.toJson(jsonSensorAlarm));  已经通过redis warningNotify 通知
            }

        } else {
            log.info("上报数据解析失败：" + str);
        }
    }



    public void controlOpenAnswer(String str) {
        JsonControl jsonControl = fromJson(str, JsonControl.class);
        if (jsonControl != null) {

        } else {
            log.info("控制开功能数据解析失败：" + str);
        }
    }

    public void controlCloseAnswer(String str) {
        JsonControl jsonControl = fromJson(str, JsonControl.class);
        if (jsonControl != null) {

        } else {
            log.info("控制关功能数据解析失败：" + str);
        }
    }

    /**
     * 检测是否已经登陆
     *
     * @param session
     */
    public boolean checkUserlogin(IoSession session) {
        if (!userRegistry.existsBySessionId(session.getId() + "")) {
            JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.not_login);
            session.write(toJson(jsonAnswer));
            session.closeNow();
            return false;
        }
        return true;
    }

    public synchronized void userlogin(String str, IoSession session) {
        JsonUserLogin jsonUserLogin = fromJson(str, JsonUserLogin.class);
        log.info("app登陆：" + JsonHelper.toJson(jsonUserLogin));
        if (jsonUserLogin == null || jsonUserLogin.getUserName() == null || jsonUserLogin.getUserPassword() == null) {
            JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.protocol_error);
            session.write(toJson(jsonAnswer));
            session.closeNow();
            return;
        }
        userRegistry.register(jsonUserLogin.getUserName(), jsonUserLogin.getUserPassword(), session);
    }

    public synchronized void getDevicePage(String str, IoSession session) {
        JsonDevicePage jsonDevicePage = fromJson(str, JsonDevicePage.class);
        log.info("app获取设备页面：" + JsonHelper.toJson(jsonDevicePage));

        userRegistry.SendDevicePage(jsonDevicePage.getPage(), jsonDevicePage.getLimit(), session);
    }

    public synchronized void getDeviceSenator(String str, IoSession session) {
        JsonControl jsonControl = fromJson(str, JsonControl.class);
        log.info("app获取设备详情：" + JsonHelper.toJson(jsonControl));
        userRegistry.SendDeviceSenator(jsonControl.getDeviceId(), session);
    }

//    public static void main(String[] args) {
//        try {
//            JsonReportedData jsonReportedData =new JsonReportedData();
//            jsonReportedData.setTemperature(20.0);
//            jsonReportedData.setPm25(50.0);
//            jsonReportedData.setHumidity(50.0);
//            jsonReportedData.setOpticalInductor(50.0);
//            jsonReportedData.setSmokeSensors(0);
//            jsonReportedData.setBodyInductor(0);
//            jsonReportedData.setIlluminatingBrightness(0.0);
//            jsonReportedData.setLatlng("[113.291472,23.148949]");
//
//            System.out.println(toJson(jsonReportedData));
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

}