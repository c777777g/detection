package com.detection.interfaces.controller.device2http;

import com.commonsl.json.JsonControl;
import com.commonsl.json.ProtocolKey;
import com.commonsl.model.*;
import com.commonsl.service.DeviceService;
import com.commonsl.service.LogService;
import com.commonsl.util.ErrorCode;
import com.commonsl.util.JsonResult;
import com.commonsl.util.Redis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/remoteMgt")
@Controller
public class RemoteController {
    private Logger logger = Logger.getLogger(RemoteController.class);

    final String imgPath = "/opt/audio/";
//    final String imgPath = "";

    @Autowired
    private Redis redis;

    @Autowired
    private LogService logService;
    @Autowired
    DeviceService deviceService;


    @RequestMapping("/controlIlluminating")
    @ResponseBody
    public JsonResult controlIlluminating(HttpServletRequest request, String deviceId, Double illuminatingBrightness, Integer flash) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }
        if (illuminatingBrightness == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "亮度不能为空"));
            return jsonResult;
        }
        if (flash == null) {
            flash = 0;
        }

        JsonControl jsonControl = new JsonControl();
        jsonControl.setOpcode(ProtocolKey.controlOpen);
        jsonControl.setDeviceId(deviceId);
        jsonControl.setFlash(flash);
        jsonControl.setIlluminatingBrightness(illuminatingBrightness);
        redis.convertAndSend("control", jsonControl.toJson());

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());
        log.setMessage("开灯亮度：" + illuminatingBrightness);
        log.setDeviceName(device.getDeviceName());
        log.setDeviceId(device.getDeviceId());
        logService.save(log);

        return jsonResult;
    }


    @RequestMapping("/closeIlluminating")
    @ResponseBody
    public JsonResult closeIlluminating(HttpServletRequest request, String deviceId) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        if (deviceId == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "设备id不能为空"));
            return jsonResult;
        }

        JsonControl jsonControl = new JsonControl();
        jsonControl.setOpcode(ProtocolKey.controlClose);
        jsonControl.setDeviceId(deviceId);
        jsonControl.setIlluminatingBrightness((double)1024);
        redis.convertAndSend("control", jsonControl.toJson());

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());
        log.setMessage("关灯");
        log.setDeviceName(device.getDeviceName());
        log.setDeviceId(device.getDeviceId());
        logService.save(log);
        return jsonResult;
    }

} 