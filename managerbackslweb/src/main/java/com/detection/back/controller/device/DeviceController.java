package com.detection.back.controller.device;

import com.commonsl.json.JsonSetDevice;
import com.commonsl.model.*;
import com.commonsl.service.*;
import com.commonsl.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RequestMapping("/deviceMgt")
@Controller
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceLimitService deviceLimitService;
    @Autowired
    DeviceSensorService deviceSensorService;

    @Autowired
    private DeviceIlluminatingTaskService deviceIlluminatingTaskService;


    @Autowired
    private LogService logService;

    @Autowired
    private Redis redis;

    private Logger logger = Logger.getLogger(DeviceController.class);
//    @RequestMapping(value = "/homeDevcie", method = RequestMethod.GET)
//    public String test(HttpServletRequest request) {
//
//        request.setAttribute("menus", institutionsService.getMenuListByAdmin(u.getUserName(), u.getRole()));
//        return "device/homePage";
//    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletResponse response, HttpServletRequest request, Entity.Pagination pagination) {
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        pagination = deviceService.selectPage(deviceCriteria, pagination);
        if (pagination.getPage() == 0) {
            pagination.setPage(1);
        }
        request.setAttribute("pagination", pagination);
        return "device/grading";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String edit(HttpServletResponse response, HttpServletRequest request, Integer id) {
        Device device = deviceService.selectOne(id);
        request.setAttribute("bean", device);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        DeviceLimit deviceLimit = deviceLimitService.selectOne(deviceLimitCriteria);
        request.setAttribute("bean1", deviceLimit);

        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);
        request.setAttribute("deviceSensor", deviceSensor);
        return "device/device_setting";
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String deviceShow(HttpServletRequest request, String deviceId) {

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        request.setAttribute("bean", device);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceLimit deviceLimit = deviceLimitService.selectOne(deviceLimitCriteria);
        request.setAttribute("deviceLimit", deviceLimit);

        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceSensorCriteria);
        request.setAttribute("deviceSensor", deviceSensor);

        return "device/device_show";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonResult save(HttpServletRequest request, Device device, DeviceLimit deviceLimit, DeviceSensor deviceSensor, String type) {
        logger.info("设备信息：");
        logger.info(device.toString());
        logger.info(deviceLimit.toString());
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (device.getDeviceId() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        Device device1 = deviceService.selectOne(deviceCriteria);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        DeviceLimit deviceLimit1 = deviceLimitService.selectOne(deviceLimitCriteria);

        if (device1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
        } else {
            device1.setDeviceAddress(device.getDeviceAddress());
            device1.setDeviceName(device.getDeviceName());
            device1.setDistrict(device.getDistrict());
            deviceService.update(device1);

            Log log = new Log();
            log.setUserName(u.getUserName());
            log.setType(3);
            log.setDeviceName(device1.getDeviceName());
            log.setDeviceId(device1.getDeviceId());
            log.setCreateTime(new Date());
            log.setMessage("修改设备信息为：" + JsonHelper.toJson(device));
            logService.save(log);

        }

        if (deviceLimit1 == null) {
//            deviceLimit.setId(CommonUtil.createUserCode());
            deviceLimitService.save(deviceLimit);
        } else {
            deviceLimit1.setTemperatureMax(deviceLimit.getTemperatureMax());
            deviceLimit1.setTemperatureMin(deviceLimit.getTemperatureMin());
            deviceLimit1.setHumidityMax(deviceLimit.getHumidityMax());
            deviceLimit1.setHumidityMin(deviceLimit.getHumidityMin());
            deviceLimit1.setPm25Max(deviceLimit.getPm25Max());
            deviceLimit1.setPm25Min(deviceLimit.getPm25Min());
            deviceLimit1.setOpticalInductorMax(deviceLimit.getOpticalInductorMax());
            deviceLimit1.setOpticalInductorMin(deviceLimit.getOpticalInductorMin());
            deviceLimitService.update(deviceLimit1);
        }

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(3);
        log.setDeviceName(device1.getDeviceName());
        log.setDeviceId(device1.getDeviceId());
        log.setCreateTime(new Date());
        log.setMessage("修改设备预警信息为：" + JsonHelper.toJson(deviceLimit));
        logService.save(log);

        logger.info("deviceSensor是：" + JsonHelper.toJson(deviceSensor));
        return jsonResult;
    }

    @RequestMapping("/saveDeviceLimit")
    @ResponseBody
    public JsonResult saveDeviceLimit(HttpServletRequest request, DeviceLimit deviceLimit) {
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
            deviceLimit1.setTemperatureMax(deviceLimit.getTemperatureMax());
            deviceLimit1.setTemperatureMin(deviceLimit.getTemperatureMin());
            deviceLimit1.setHumidityMax(deviceLimit.getHumidityMax());
            deviceLimit1.setHumidityMin(deviceLimit.getHumidityMin());
            deviceLimit1.setPm25Max(deviceLimit.getPm25Max());
            deviceLimit1.setPm25Min(deviceLimit.getPm25Min());
            deviceLimit1.setOpticalInductorMax(deviceLimit.getOpticalInductorMax());
            deviceLimit1.setOpticalInductorMin(deviceLimit.getOpticalInductorMin());
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

//    @RequestMapping(value = "/map", method = RequestMethod.GET)
//    public String map(HttpServletRequest request, Entity.Pagination pagination) {
//        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
//        List<DeviceSensor> deviceSensorList = deviceSensorService.selectAll();
//        request.setAttribute("deviceSensorList", deviceSensorList);
//
//        return "device/map";
//    }

    @RequestMapping(value = "/changesAlarm", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changesAlarm(HttpServletRequest request, String deviceId, Integer type) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }
        if (type == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "类型不能为空"));
            return jsonResult;
        }
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device1 = deviceService.selectOne(deviceCriteria);

        Entity.DeviceLimitCriteria deviceLimitCriteria = new Entity.DeviceLimitCriteria();
        deviceLimitCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceLimit deviceLimit1 = deviceLimitService.selectOne(deviceLimitCriteria);

        if (device1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
            return jsonResult;
        }

        if (deviceLimit1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
            return jsonResult;
        } else {
            int i = 0;
            switch (type) {
                case 1:
                    if (deviceLimit1.getTemperatureAlarm() != null && deviceLimit1.getTemperatureAlarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setTemperatureAlarm(i);
                    break;
                case 2:
                    if (deviceLimit1.getHumidityAlarm() != null && deviceLimit1.getHumidityAlarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setHumidityAlarm(i);
                    break;
                case 3:
                    if (deviceLimit1.getPm25Alarm() != null && deviceLimit1.getPm25Alarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setPm25Alarm(i);
                    break;
                case 4:
                    if (deviceLimit1.getOpticalInductorAlarm() != null && deviceLimit1.getOpticalInductorAlarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setOpticalInductorAlarm(i);
                    break;
                case 5:
                    if (deviceLimit1.getBodyInductoAlarm() != null && deviceLimit1.getBodyInductoAlarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setBodyInductoAlarm(i);
                    break;
                case 6:
                    if (deviceLimit1.getSmokeSensorsAlarm() != null && deviceLimit1.getSmokeSensorsAlarm() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setSmokeSensorsAlarm(i);
                    break;
                case 7:
                    if (deviceLimit1.getMode() != null && deviceLimit1.getMode() == 0) {
                        i = 1;
                    }
                    deviceLimit1.setMode(i);
                    break;
            }
            deviceLimitService.update(deviceLimit1);
        }

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(3);
        log.setDeviceId(deviceId);
        log.setCreateTime(new Date());
        log.setMessage("修改设备预警信息为：" + JsonHelper.toJson(deviceLimit1));
        logService.save(log);
        return jsonResult;
    }


    @RequestMapping(value = "/map")
    public String deviceMap(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("USER");
        List<Device> deviceList = deviceService.selectAll();
        List<DeviceSensor> deviceSensorList = deviceSensorService.selectAll();

        List<DeviceShow> deviceShowList = new ArrayList<>();
        for (Device device : deviceList) {
            for (DeviceSensor deviceSensor : deviceSensorList) {
                if (device.getDeviceId().equals(deviceSensor.getDeviceId())) {
                    deviceShowList.add(new DeviceShow(device, deviceSensor, null));
                }
            }
        }

        String data = null;
        for (DeviceShow d : deviceShowList) {
            if (d.getDeviceSensor().getLatlng() != null && !"".equals(d.getDeviceSensor().getLatlng())) {
                String state = "在线";
                if (d.getState() == 1) {
                    state = "在线";
                } else {
                    state = "离线";
                }
                if (data != null)
                    data += d.getDeviceSensor().getLatlng().replace("]", ",\"") + "设备名:" + d.getDeviceName() + "<br />地址:" + d.getDeviceAddress() + "<br />状态:" + state + "\"]" + ",";
                else
                    data = d.getDeviceSensor().getLatlng().replace("]", ",\"") + "设备名:" + d.getDeviceName() + "<br />地址:" + d.getDeviceAddress() + "<br />状态:" + state + "\"]" + ",";
            }
        }
        request.setAttribute("MARKER", data);
        return "device/map3";
    }





    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(HttpServletRequest request, Integer id) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        Device device = deviceService.selectOne(id);

        Entity.DeviceIlluminatingTaskCriteria deviceIlluminatingTaskCriteria = new Entity.DeviceIlluminatingTaskCriteria();
        deviceIlluminatingTaskCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        deviceIlluminatingTaskService.delete(deviceIlluminatingTaskCriteria);


        deviceService.delete(id);
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(3);
        log.setDeviceName(device.getDeviceName());
        log.setDeviceId(device.getDeviceId());
        log.setCreateTime(new Date());
        log.setMessage("删除设备：" + device.getDeviceName());
        logService.save(log);

        return jsonResult;
    }


    @RequestMapping(value = "/explore", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult explore(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
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

    //设置界面
    @RequestMapping("/setBasicInformation")
    @ResponseBody
    public JsonResult setBasicInformation(HttpServletRequest request, Device device) {
        logger.info("设备信息：" + device.toString());
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        if (device.getDeviceId() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }
        if (device.getDeviceName() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备名不能为空"));
            return jsonResult;
        }
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(device.getDeviceId()));
        Device device1 = deviceService.selectOne(deviceCriteria);

        Entity.DeviceCriteria deviceCriteria2 = new Entity.DeviceCriteria();
        deviceCriteria2.setDeviceName(Entity.Value.eq(device.getDeviceName()));
        Device device2 = deviceService.selectOne(deviceCriteria2);

        if (device2 != null && !(device1.getDeviceId().equals(device2.getDeviceId()))) {
                jsonResult.addErrorCode(new ErrorCode(100003, "设备名不能和其它设备重复"));
                return jsonResult;
        }


        if (device1 == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id有误"));
        } else {
            device1.setDeviceAddress(device.getDeviceAddress());
            device1.setDeviceName(device.getDeviceName());
            device1.setDistrict(device.getDistrict());
            deviceService.update(device1);

            Log log = new Log();
            log.setUserName(u.getUserName());
            log.setType(3);
            log.setDeviceName(device1.getDeviceName());
            log.setDeviceId(device1.getDeviceId());
            log.setCreateTime(new Date());
            log.setMessage("修改设备信息为：" + JsonHelper.toJson(device));
            logService.save(log);

        }
        return jsonResult;
    }

    @RequestMapping("/setIp")
    @ResponseBody
    public JsonResult setIp(HttpServletRequest request, Device device) {
        logger.info("设备信息：" + device.toString());
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
        } else {
            device1.setIpSetStatic(device.getIpSetStatic());
            device1.setStaticIp(device.getStaticIp());
            device1.setStaticSubnetMask(device.getStaticSubnetMask());
            device1.setStaticGateway(device.getStaticGateway());
            deviceService.update(device1);

            JsonSetDevice jsonSetDevice =new JsonSetDevice();
            jsonSetDevice.setOpcode("setIP");
            jsonSetDevice.setGateway(device.getStaticGateway());
            jsonSetDevice.setIPAddress(device.getStaticIp());
            jsonSetDevice.setSubnetMask(device.getStaticSubnetMask());
            jsonSetDevice.setDeviceId(device.getDeviceId());
            redis.convertAndSend("setDevice", JsonHelper.toJson(jsonSetDevice));

            Log log = new Log();
            log.setUserName(u.getUserName());
            log.setType(3);
            log.setDeviceName(device1.getDeviceName());
            log.setDeviceId(device1.getDeviceId());
            log.setCreateTime(new Date());
            log.setMessage("修改设备信息为：" + JsonHelper.toJson(device));
            logService.save(log);

        }
        return jsonResult;
    }

    @RequestMapping("/setWifi")
    @ResponseBody
    public JsonResult setWifi(HttpServletRequest request, Device device) {
        logger.info("设备信息：" + device.toString());
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
        } else {
            device1.setSsid(device.getSsid());
            device1.setWifiPassword(device.getWifiPassword());
            deviceService.update(device1);

            Log log = new Log();
            log.setUserName(u.getUserName());
            log.setType(3);
            log.setDeviceName(device1.getDeviceName());
            log.setDeviceId(device1.getDeviceId());
            log.setCreateTime(new Date());
            log.setMessage("修改设备信息为：" + JsonHelper.toJson(device));
            logService.save(log);
        }
        return jsonResult;
    }


    @RequestMapping("/setBluetooth")
    @ResponseBody
    public JsonResult setBluetooth(HttpServletRequest request, Device device) {
        logger.info("设备信息：" + device.toString());
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
        } else {
            device1.setBluetoothId(device.getBluetoothId());
            deviceService.update(device1);

            Log log = new Log();
            log.setUserName(u.getUserName());
            log.setType(3);
            log.setDeviceName(device1.getDeviceName());
            log.setDeviceId(device1.getDeviceId());
            log.setCreateTime(new Date());
            log.setMessage("修改设备信息为：" + JsonHelper.toJson(device));
            logService.save(log);
        }
        return jsonResult;
    }

} 