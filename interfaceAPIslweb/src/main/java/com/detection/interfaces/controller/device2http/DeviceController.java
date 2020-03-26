package com.detection.interfaces.controller.device2http;

import com.commonsl.json.JsonSetDevice;
import com.commonsl.json.ProtocolKey;
import com.commonsl.model.*;
import com.commonsl.service.*;
import com.commonsl.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RequestMapping("/deviceMgt")
@Controller
public class DeviceController {
    private Logger logger = Logger.getLogger(DeviceController.class);
    @Autowired
    private DeviceService deviceService;

    @Autowired
    DeviceLimitService deviceLimitService;

    @Autowired
    DeviceSensorService deviceSensorService;



    @Autowired
    private LogService logService;


    @Autowired
    private Redis redis;


    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @RequestMapping("/deviceList")
    @ResponseBody
    public JsonResult deviceList(HttpServletRequest request, Entity.Pagination pagination) {
        logger.info("getSession++++++++++++++++:" + request.getSession().getId());
        User user = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
//        if (user == null) {
//            jsonResult.addErrorCode(new ErrorCode(100003, "用户登陆过期"));
//            return jsonResult;
//        }

        List<DeviceShow> deviceShowList = new ArrayList<>();

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.isNotNull());
        pagination = deviceService.selectPage(deviceCriteria, pagination);
        List<Device> deviceList = (List<Device>) pagination.getRows();
        for (Device device : deviceList) {
            Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
            deviceSensorCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
            DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);


            deviceShowList.add(new DeviceShow(device, deviceSensor));
        }
        pagination.setRows(deviceShowList);

        jsonResult.setData(pagination);
        return jsonResult;
    }

    @RequestMapping("/deviceList2")
    @ResponseBody
    public JsonResult deviceList2(HttpServletRequest request, Entity.Pagination pagination) {
        logger.info("getSession++++++++++++++++:" + request.getSession().getId());
        User user = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
//        if (user == null) {
//            jsonResult.addErrorCode(new ErrorCode(100003, "用户登陆过期"));
//            return jsonResult;
//        }
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.isNotNull());
        pagination = deviceService.selectPage(deviceCriteria, pagination);
        jsonResult.setData(pagination);
        return jsonResult;
    }

    @RequestMapping("/device")
    @ResponseBody
    public JsonResult device(HttpServletRequest request, String deviceId) {
        logger.info("getSession++++++++++++++++:" + request.getSession().getId());
        User user = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
//        if (user == null) {
//            jsonResult.addErrorCode(new ErrorCode(100003, "用户登陆过期"));
//            return jsonResult;
//        }
        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100004, "deviceId为空"));
            return jsonResult;
        }

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        if (device == null) {
            jsonResult.addErrorCode(new ErrorCode(100004, "deviceId无效"));
            return jsonResult;
        }
        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);

        jsonResult.setData(new DeviceShow(device, deviceSensor));
        return jsonResult;
    }

    @RequestMapping("/volume")
    @ResponseBody
    public JsonResult volume(HttpServletRequest request, String deviceId, Integer volume) {
        User user = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100004, "deviceId为空"));
            return jsonResult;
        }
        if (volume == null) {
            jsonResult.addErrorCode(new ErrorCode(100004, "volume为空"));
            return jsonResult;
        }
        if (user == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "用户登陆过期"));
            return jsonResult;
        }

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        if (device == null) {
            jsonResult.addErrorCode(new ErrorCode(100004, "deviceId无效"));
            return jsonResult;
        }

        JsonSetDevice jsonSetDevice = new JsonSetDevice();
        jsonSetDevice.setVolume(volume);
        jsonSetDevice.setDeviceId(device.getDeviceId());
        jsonSetDevice.setOpcode(ProtocolKey.setDevice);

        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);
        deviceSensorService.update(deviceSensor);

        logger.info("发送远程设置：" + JsonHelper.toJson(jsonSetDevice));
        redis.convertAndSend("setDevice", JsonHelper.toJson(jsonSetDevice));

        Log log1 = new Log();
        log1.setUserName(user.getUserName());
        log1.setType(3);
        log1.setDeviceName(device.getDeviceName());
        log1.setCreateTime(new Date());
        log1.setMessage("修改设备音量为：" + volume);
        logService.save(log1);
        return jsonResult;
    }

    @RequestMapping(value = "/explore", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult explore(HttpServletRequest request) {
        JsonResult jsonResult = new JsonResult();
        User u = (User) request.getSession().getAttribute("USER");

        if (u == null) {
            jsonResult.addErrorCode(new ErrorCode(100006, "用户未登录"));
            return jsonResult;
        }

        try {
            MultiCastUtil multiCastUtil = new MultiCastUtil();
            multiCastUtil.send();
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.addErrorCode(new ErrorCode(100006, "未知错误"));
            return jsonResult;
        }
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());
        log.setMessage("触发探索设备事件");
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping("/getSensorLimit")
    @ResponseBody
    public JsonResult setTemperatureAlarm(HttpServletRequest request, String deviceId) {
        logger.info("设备信息：" + deviceId);
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceLimit deviceLimit1 = deviceLimitService.selectOne(deviceLimitCriteria);
        if (deviceLimit1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id无效"));
            return jsonResult;
        }
        jsonResult.setData(deviceLimit1);
        return jsonResult;
    }

    @RequestMapping("/setSensorLimit")
    @ResponseBody
    public JsonResult setSensorLimit(HttpServletRequest request, DeviceLimit deviceLimit) {
        logger.info("设备信息：");
        logger.info(deviceLimit.toString());
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (deviceLimit.getDeviceId() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceLimit.getDeviceId()));
        Device device1 = deviceService.selectOne(deviceCriteria);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(deviceLimit.getDeviceId()));
        DeviceLimit deviceLimit1 = deviceLimitService.selectOne(deviceLimitCriteria);

        if (device1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
        }

        if (deviceLimit1 == null) {
            deviceLimitService.save(deviceLimit);
        } else {
            if (deviceLimit.getTemperatureMax() != null) {
                deviceLimit1.setTemperatureMax(deviceLimit.getTemperatureMax());
            }
            if (deviceLimit.getTemperatureMin() != null) {
                deviceLimit1.setTemperatureMin(deviceLimit.getTemperatureMin());
            }
            if (deviceLimit.getHumidityMax() != null) {
                deviceLimit1.setHumidityMax(deviceLimit.getHumidityMax());
            }
            if (deviceLimit.getHumidityMin() != null) {
                deviceLimit1.setHumidityMin(deviceLimit.getHumidityMin());
            }
            if (deviceLimit.getPm25Max() != null) {
                deviceLimit1.setPm25Max(deviceLimit.getPm25Max());
            }
            if (deviceLimit.getPm25Min() != null) {
                deviceLimit1.setPm25Min(deviceLimit.getPm25Min());
            }
            if(deviceLimit.getTemperatureAlarm()!=null){
                deviceLimit1.setTemperatureAlarm(deviceLimit.getTemperatureAlarm());
            }
            if(deviceLimit.getHumidityAlarm()!=null){
                deviceLimit1.setHumidityAlarm(deviceLimit.getHumidityAlarm());
            }
            if(deviceLimit.getPm25Alarm()!=null){
                deviceLimit1.setPm25Alarm(deviceLimit.getPm25Alarm());
            }
            if(deviceLimit.getSmokeSensorsAlarm()!=null){
                deviceLimit1.setSmokeSensorsAlarm(deviceLimit.getSmokeSensorsAlarm());
            }
            if(deviceLimit.getBodyInductoAlarm()!=null){
                deviceLimit1.setBodyInductoAlarm(deviceLimit.getBodyInductoAlarm());
            }
            deviceLimitService.update(deviceLimit1);
        }

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(3);
        log.setDeviceName(device1.getDeviceName());
        log.setDeviceId(device1.getDeviceId());
        log.setCreateTime(new Date());
        log.setMessage("修改设备预警信息为：" + JsonHelper.toJson(deviceLimit1));
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping("/getDevcieConfiguration")
    @ResponseBody
    public JsonResult getDevcieConfiguration(HttpServletRequest request, String deviceId) {
        logger.info("设备信息：" + deviceId);
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        if (device == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id无效"));
            return jsonResult;
        }
        jsonResult.setData(device);
        return jsonResult;
    }

    @RequestMapping("/setDevcieConfiguration")
    @ResponseBody
    public JsonResult setDevcieConfiguration(HttpServletRequest request, Device device) {
        logger.info("设备信息："+device.toString());
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (device.getDeviceId() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }


        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        Device device1 = deviceService.selectOne(deviceCriteria);



        if (device1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
        }else {
            if(device.getBluetoothId()!=null){
                device1.setBluetoothId(device.getBluetoothId());
            }
            if(device.getDeviceAddress()!=null){
                device1.setDeviceAddress(device.getDeviceAddress());
            }
            if(device.getDeviceName()!=null){
                //防止设备名重复
                Entity.DeviceCriteria deviceCriteria2 = new Entity.DeviceCriteria();
                deviceCriteria2.setDeviceName(Entity.Value.eq(device.getDeviceName()));
                Device device2 = deviceService.selectOne(deviceCriteria2);

                if (device2 != null && !(device1.getDeviceId().equals(device2.getDeviceId()))) {
                        jsonResult.addErrorCode(new ErrorCode(100003, "设备名不能和其它设备重复"));
                        return jsonResult;
                }
                device1.setDeviceName(device.getDeviceName());
            }
            if(device.getDistrict()!=null){
                device1.setDistrict(device.getDistrict());
            }
            if(device.getIp()!=null){
                device1.setIp(device.getIp());
            }
            if(device.getIpSetStatic()!=null){
                device1.setIpSetStatic(device.getIpSetStatic());
            }
            if(device.getSsid()!=null){
                device1.setSsid(device.getSsid());
            }
            if(device.getStaticGateway()!=null){
                device1.setStaticGateway(device.getStaticGateway());
            }
            if(device.getStaticIp()!=null){
                device1.setStaticIp(device.getStaticIp());
            }
            if(device.getStaticSubnetMask()!=null){
                device1.setStaticSubnetMask(device.getStaticSubnetMask());
            }
            if(device.getWifiPassword()!=null){
                device1.setWifiPassword(device.getWifiPassword());
            }
            deviceService.update(device1);
        }

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(3);
        log.setDeviceName(device1.getDeviceName());
        log.setDeviceId(device1.getDeviceId());
        log.setCreateTime(new Date());
        log.setMessage("修改设备预警信息为：" + JsonHelper.toJson(device1));
        logService.save(log);
        return jsonResult;
    }


    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(HttpServletRequest request, Integer id) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        Device device = deviceService.selectOne(id);
        deviceService.delete(id);

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());
        log.setMessage("删除设备：" + device);
        logService.save(log);

        return jsonResult;
    }

}
