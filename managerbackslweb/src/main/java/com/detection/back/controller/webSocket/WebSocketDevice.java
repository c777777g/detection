package com.detection.back.controller.webSocket;

import com.commonsl.model.Device;
import com.commonsl.model.DeviceSensor;
import com.commonsl.util.JsonHelper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

public class WebSocketDevice {


    private final WebSocketSession webSocketSession;


    private final String deviceId;
    private DeviceSensor deviceSensor;
    private Device device;


    public WebSocketDevice(String deviceId, WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
        this.deviceId = deviceId;
    }

    public void sendDeviceSensor() {
        try {
            String m = JsonHelper.toJson(deviceSensor);
            webSocketSession.sendMessage(new TextMessage(m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendDeviceSensor(Device device,DeviceSensor deviceSensor) {
        try {
            setDeviceSensor(deviceSensor);
            setDevice(device);
            Map<String,Object> map = new HashMap<>();
            map.put("device",device);
            map.put("deviceSensor",deviceSensor);
            String m = JsonHelper.toJson(map);
            webSocketSession.sendMessage(new TextMessage(m));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }


    public DeviceSensor getDeviceSensor() {
        return deviceSensor;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceSensor(DeviceSensor deviceSensor) {
        this.deviceSensor = deviceSensor;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}