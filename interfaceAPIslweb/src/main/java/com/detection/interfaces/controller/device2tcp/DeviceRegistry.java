package com.detection.interfaces.controller.device2tcp;

import com.commonsl.json.*;
import com.commonsl.model.*;
import com.commonsl.service.*;
import com.commonsl.util.JsonHelper;
import com.commonsl.util.Redis;
import com.commonsl.util.RedisKey;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.commonsl.util.JsonHelper.toJson;

@Component
public class DeviceRegistry {
    private Logger log = Logger.getLogger(DeviceRegistry.class);
    private static final ConcurrentHashMap<String, DeviceSession> devicesById = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, DeviceSession> devicesBySessionId = new ConcurrentHashMap<>();


    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceLimitService deviceLimitService;
    @Autowired
    private DeviceSensorService deviceSensorService;

    @Autowired
    private DeviceIlluminatingTaskService deviceIlluminatingTaskService;


    @Autowired
    private Redis redis;

    private static Timer timerPlay = new Timer();
    private static boolean timerPlayLock = true;



    /**
     * 登陆
     *
     * @param devcieId
     * @param ioSession
     */
    public void register(String devcieId, IoSession ioSession) {
        DeviceSession u = devicesById.get(devcieId);
        if (null != u) {
            devicesBySessionId.remove(u.getSession().getId() + "");
            devicesById.remove(u.getDeviceId());
        }
        InetSocketAddress adress = (InetSocketAddress) ioSession.getRemoteAddress();
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(devcieId));
        Device device = deviceService.selectOne(deviceCriteria);
        if (device == null) {
            //创建新的设备对象
            device = new Device();
            device.setDeviceId(devcieId);
            device.setDeviceName(devcieId);
            device.setIp(adress.getAddress() + "");
            device.setState(1);
            device.setCreateTime(new Date());
            createDevice(device);
        }
        device.setIp(adress.getHostString());
        device.setState(1);
        deviceService.update(device);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(devcieId));
        DeviceLimit deviceLimit = deviceLimitService.selectOne(deviceCriteria);
        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(devcieId));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);
        DeviceSession deviceSession = new DeviceSession(devcieId, ioSession, device, deviceLimit, deviceSensor, this);
        devicesById.put(devcieId, deviceSession);
        devicesBySessionId.put(ioSession.getId() + "", deviceSession);
        device.setState(1);
        deviceService.update(device);

        JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 1, ProtocolKey.success);
        deviceSession.sendMessage(toJson(jsonAnswer));

        //缓存到redis，等后台页面拿
        JsonSensorAlarm jsonSensorAlarm = new JsonSensorAlarm();
        jsonSensorAlarm.setDeviceId(deviceSession.getDeviceId());
        jsonSensorAlarm.setDeviceName(deviceSession.getDevice().getDeviceName());
        jsonSensorAlarm.setStateAddMessage(1);
        String j = JsonHelper.toJson(jsonSensorAlarm);
        redis.setex(String.format(RedisKey.SENSORALARM, jsonSensorAlarm.getDeviceId()), j, 60);
    }

    public DeviceSession getById(String id) {
        return devicesById.get(id);
    }

    public DeviceSession getBySession(IoSession session) {
        return devicesBySessionId.get(session.getId() + "");
    }

    public boolean existsByDeviceId(String id) {
        return devicesById.containsKey(id);
    }

    public boolean existsBySessionId(String id) {
        return devicesBySessionId.containsKey(id);
    }

    /**
     * 设备离线
     *
     * @param session
     * @return
     */
    public DeviceSession removeBySession(IoSession session) {

        final DeviceSession deviceSession = getBySession(session);
        if (deviceSession != null) {
            deviceSession.device.setState(2);
            deviceService.update(deviceSession.device);

            devicesById.remove(deviceSession.getDeviceId());
            devicesBySessionId.remove(session.getId());
            deviceSession.close();
        }

        //缓存到redis，等后台页面拿
        JsonSensorAlarm jsonSensorAlarm = new JsonSensorAlarm();
        jsonSensorAlarm.setDeviceId(deviceSession.getDeviceId());
        jsonSensorAlarm.setDeviceName(deviceSession.getDevice().getDeviceName());
        jsonSensorAlarm.setStateAddMessage(2);
        String j = JsonHelper.toJson(jsonSensorAlarm);
        redis.setex(String.format(RedisKey.SENSORALARM, jsonSensorAlarm.getDeviceId()), j, 60);

        return deviceSession;
    }

    /**
     * 群发
     *
     * @param message
     */
    public void massSend(String message) {
        for (String key : devicesById.keySet()) {
            devicesById.get(key).sendMessage(message);
        }
    }

    /**
     * 群发
     *
     * @param message
     * @param devcieIdList 设备id列表
     */
    public void massSend(String message, List<String> devcieIdList) {
        for (String key : devicesById.keySet()) {
            if (devcieIdList.contains(key)) {
                devicesById.get(key).sendMessage(message);
            }
        }
    }

    /**
     * 创建新的设备对象
     *
     * @param device
     */
    public void createDevice(Device device) {
//        device.setId(CommonUtil.createUserCode());
        deviceService.save(device);

        DeviceLimit deviceLimit = new DeviceLimit();
//        deviceLimit.setId(CommonUtil.createUserCode());
        deviceLimit.setDeviceId(device.getDeviceId());
        deviceLimitService.save(deviceLimit);

        DeviceSensor deviceSensor = new DeviceSensor();
//        deviceSensor.setId(CommonUtil.createUserCode());
        deviceSensor.setDeviceId(device.getDeviceId());
        deviceSensorService.save(deviceSensor);
        log.info("创建新的设备对象:" + device.getDeviceId());
    }


    /**
     * 更新设备上报的数据
     *
     * @param jsonReportedData
     * @param deviceSession
     * @return 返回数据警报
     */
    public JsonSensorAlarm reportedData(JsonReportedData jsonReportedData, DeviceSession deviceSession) {

        DeviceSensor deviceSensorOld = deviceSession.getDeviceSensor().deviceSensorClone();

        deviceSession.setSensor(jsonReportedData);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(jsonReportedData.getDeviceId()));
        DeviceLimit deviceLimit = deviceLimitService.selectOne(deviceLimitCriteria);

        //模式检验
        if (deviceLimit.getMode() != null) {
            switch (deviceLimit.getMode()) {
                case 0:
                    break;
                case 1: //夜间模式(环境暗了开灯)
                    if (deviceLimit.getOpticalInductorMin() != null &&
                            deviceLimit.getOpticalInductorMin() > deviceSession.getDeviceSensor().getOpticalInductor() &&
                            deviceLimit.getOpticalInductorMin() < deviceSensorOld.getOpticalInductor()) {
                        JsonControl jsonControl = new JsonControl();
                        jsonControl.setOpcode(ProtocolKey.controlOpen);
                        jsonControl.setDeviceId(deviceSensorOld.getDeviceId());
                        jsonControl.setFlash(0);
                        jsonControl.setIlluminatingBrightness(100.0);
                        redis.convertAndSend("control", jsonControl.toJson());
                    }
                    if (deviceLimit.getOpticalInductorMin() != null &&
                            deviceLimit.getOpticalInductorMin() < deviceSession.getDeviceSensor().getOpticalInductor() &&
                            deviceLimit.getOpticalInductorMin() > deviceSensorOld.getOpticalInductor()) {
                        JsonControl jsonControl = new JsonControl();
                        jsonControl.setOpcode(ProtocolKey.controlClose);
                        jsonControl.setDeviceId(deviceSensorOld.getDeviceId());
                        jsonControl.setFlash(0);
                        jsonControl.setIlluminatingBrightness(0.0);
                        redis.convertAndSend("control", jsonControl.toJson());
                    }
                    break;
            }
        }

        //检测上报值是否正常
        JsonSensorAlarm jsonSensorAlarm = deviceSession.checkSensor(deviceLimit, deviceSensorOld);
        if (jsonSensorAlarm != null) {
            //缓存到redis，等后台页面拿
            String j = JsonHelper.toJson(jsonSensorAlarm);
            redis.setex(String.format(RedisKey.SENSORALARM, jsonSensorAlarm.getDeviceId()), j, 60);

            log.info("发送警报：" + JsonHelper.toJson(jsonSensorAlarm));
            redis.convertAndSend("warningNotify", JsonHelper.toJson(jsonSensorAlarm));
            return jsonSensorAlarm;
        }
        return null;

    }






    /**
     * 向设备更新任务表开灯
     *
     * @param deviceIds 设备id
     */
    public void sendIlluminatingTask(String... deviceIds) {
        for (String device : deviceIds) {
            DeviceSession deviceSession = devicesById.get(device);
            if (deviceSession != null) {
                Entity.DeviceIlluminatingTaskCriteria deviceIlluminatingTaskCriteria = new Entity.DeviceIlluminatingTaskCriteria();
                deviceIlluminatingTaskCriteria.setDeviceId(Entity.Value.eq(device));
                List<DeviceIlluminatingTask> deviceIlluminatingTaskList = deviceIlluminatingTaskService.selectList(deviceIlluminatingTaskCriteria);

                //发送消息
                JsonIlluminatingTask jsonIlluminatingTask = new JsonIlluminatingTask();
                jsonIlluminatingTask.setDeviceId(device);
                jsonIlluminatingTask.setDate(new Date().getTime() + "");
                jsonIlluminatingTask.setIlluminatingTasks(deviceIlluminatingTaskList);
                deviceSession.sendMessage(JsonHelper.toJson(jsonIlluminatingTask));
            }
        }
    }

    //供deviceSession调用更新deviceSensor
    public void updateDeviceSensor(DeviceSensor deviceSensor) {
        deviceSensorService.update(deviceSensor);
    }



    public DeviceLimit getDeviceLimit(String devcieId) {
        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(devcieId));
        return deviceLimitService.selectOne(deviceLimitCriteria);
    }


}




