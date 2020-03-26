package com.detection.interfaces.controller.user2tcp;

import com.commonsl.json.JsonAnswer;
import com.commonsl.json.JsonDevicePage;
import com.commonsl.json.JsonDeviceSenator;
import com.commonsl.json.ProtocolKey;
import com.commonsl.model.*;
import com.commonsl.service.*;
import com.commonsl.util.JsonHelper;
import com.commonsl.util.Md5SaltTool;
import com.commonsl.util.Redis;
import com.detection.interfaces.controller.device2tcp.DeviceRegistry;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.commonsl.util.JsonHelper.toJson;

@Component
public class UserRegistry {
    private Logger log = Logger.getLogger(DeviceRegistry.class);
    private static final ConcurrentHashMap<String, UserSession> userByuserName = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, UserSession> userBySessionId = new ConcurrentHashMap<>();


    @Autowired
    private Redis redis;

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceLimitService deviceLimitService;

    @Autowired
    private DeviceSensorService deviceSensorService;


    public void register(String userName, String userPassword, IoSession ioSession) {

        Entity.UserCriteria userCriteria = new Entity.UserCriteria();
        userCriteria.setUserName(Entity.Value.eq(userName));
        User user = userService.selectOne(userCriteria);
        try {
            if (user != null) {
                if (!Md5SaltTool.validPassword(userPassword, user.getUserPassword())) {
                    JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.wrong_password);
                    ioSession.write(toJson(jsonAnswer));
                    ioSession.closeNow();
                    return;
                } else {
//登陆
                }
            } else {
                JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.wrong_password);
                ioSession.write(toJson(jsonAnswer));
                ioSession.closeNow();
                return;
            }
        } catch (Exception e) {
            JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 2, ProtocolKey.wrong_password);
            ioSession.write(toJson(jsonAnswer));
            ioSession.closeNow();
            return;
        }


        UserSession u = userByuserName.get(userName);
        if (null != u) {
            userBySessionId.remove(u.getSession());
            userByuserName.remove(u.getUserName());
        }


        UserSession userSession = new UserSession(userName, ioSession, user, this);
        userByuserName.put(userName, userSession);
        userBySessionId.put(ioSession.getId() + "", userSession);

        user.setState("1");
        userService.update(user);

        JsonAnswer jsonAnswer = new JsonAnswer(ProtocolKey.loginAnswer, 1, ProtocolKey.success);
        userSession.sendMessage(toJson(jsonAnswer));
    }

    public UserSession removeBySession(IoSession session) {
        final UserSession userSession = getBySession(session);
        if (userSession != null) {
            userSession.getUser().setState("2");
            userService.update(userSession.getUser());

            userBySessionId.remove(session.getId());
            userByuserName.remove(userSession.getUser().getUserName());
            userSession.close();
        }
        return userSession;
    }

    public UserSession getBySession(IoSession session) {
        return userBySessionId.get(session.getId() + "");
    }

    public boolean existsByUserName(String id) {
        return userByuserName.containsKey(id);
    }

    public boolean existsBySessionId(String id) {
        return userBySessionId.containsKey(id);
    }

    public void massSend(String message) {
        for (String key : userBySessionId.keySet()) {
            userBySessionId.get(key).sendMessage(message);
        }
    }

    public void massSend(String message, List<String> userNameList) {
        for (String key : userByuserName.keySet()) {
            if (userNameList.contains(key)) {
                userByuserName.get(key).sendMessage(message);
            }
        }
    }

    public void SendDevicePage(int page, int limit, IoSession ioSession) {
        Entity.Pagination pagination = new Entity.Pagination();
        if (page != 0) {
            pagination.setPage(0);
        }
        if (limit != 0) {
            pagination.setLimit(20);
        }

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceName(Entity.Value.isNotNull());
        pagination = deviceService.selectPage(deviceCriteria, pagination);
        List<Device> deviceList = (List<Device>) pagination.getRows();

        final UserSession userSession = getBySession(ioSession);

        JsonDevicePage jsonDevicePage = new JsonDevicePage();
        jsonDevicePage.setLimit(pagination.getLimit());
        jsonDevicePage.setPage(pagination.getPage());
        jsonDevicePage.setPages(pagination.getPages());
        jsonDevicePage.setDeviceList(deviceList);
        jsonDevicePage.setOpcode(ProtocolKey.devicePage);
        jsonDevicePage.setTotal(pagination.getTotal());

        userSession.sendMessage(JsonHelper.toJson(jsonDevicePage));

    }

    public void SendDeviceSenatorToAll(String deviceId) {
        for (UserSession userSession : userBySessionId.values()) {
            SendDeviceSenator(deviceId,userSession.getSession());
        }
    }

    public void SendDeviceSenator(String deviceId, IoSession ioSession) {

        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        deviceCriteria.setDeviceId(Entity.Value.eq(deviceId));
        Device device = deviceService.selectOne(deviceCriteria);

        Entity.DeviceSensorCriteria deviceSensorCriteria = new Entity.DeviceSensorCriteria();
        deviceSensorCriteria.setDeviceId(Entity.Value.eq(deviceId));
        DeviceSensor deviceSensor = deviceSensorService.selectOne(deviceCriteria);


        final UserSession userSession = getBySession(ioSession);

        JsonDeviceSenator jsonDevicePage = new JsonDeviceSenator(ProtocolKey.deviceSenator, device, deviceSensor);

        userSession.sendMessage(JsonHelper.toJson(jsonDevicePage));

    }


}