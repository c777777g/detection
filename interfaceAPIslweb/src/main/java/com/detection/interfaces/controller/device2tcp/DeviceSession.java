package com.detection.interfaces.controller.device2tcp;

import com.commonsl.json.JsonSensorAlarm;
import com.commonsl.model.Device;
import com.commonsl.model.DeviceLimit;
import com.commonsl.model.DeviceSensor;
import org.apache.log4j.Logger;
import org.apache.mina.core.session.IoSession;


public class DeviceSession {
    private final Logger log = Logger.getLogger(DeviceSession.class);
    private final String deviceId;
    private final IoSession session;
    final Device device;
    //   private final DeviceLimit deviceLimit;
    private final DeviceSensor deviceSensor;
    private int updataCount = 0;
    private DeviceRegistry deviceRegistry;

    public DeviceSession(final String deviceId, IoSession session, Device device, DeviceLimit deviceLimit, DeviceSensor deviceSensor, DeviceRegistry deviceRegistry) {
        this.deviceId = deviceId;
        this.session = session;
        this.device = device;
        //   this.deviceLimit = deviceLimit;
        this.deviceSensor = deviceSensor;
        this.deviceRegistry = deviceRegistry;
    }

    public DeviceSensor getDeviceSensor() {
        return deviceSensor;
    }

    public Device getDevice() {
        return device;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public IoSession getSession() {
        return session;
    }

    public void sendMessage(String message) {
        session.write(message);
        log.info(deviceId + "-" + ":send message:" + message);
    }

    /**
     * 向数据库更新状态
     *
     * @param jsonReportedData
     */
    public void setSensor(com.commonsl.json.JsonReportedData jsonReportedData) {
        //传感器数据
        deviceSensor.setTemperature(jsonReportedData.getTemperature());
        deviceSensor.setPm25(jsonReportedData.getPm25());
        deviceSensor.setHumidity(jsonReportedData.getHumidity());
        deviceSensor.setOpticalInductor(jsonReportedData.getOpticalInductor());
        deviceSensor.setSmokeSensors(jsonReportedData.getSmokeSensors());
        deviceSensor.setIlluminatingBrightness(jsonReportedData.getIlluminatingBrightness());
        deviceSensor.setLatlng(jsonReportedData.getLatlng());
        deviceSensor.setBodyInductor(jsonReportedData.getBodyInductor());

        deviceRegistry.updateDeviceSensor(deviceSensor);
//        }
//        updataCount++;

    }


    /**
     * 检测数据是否正常
     */
    public JsonSensorAlarm checkSensor(DeviceLimit deviceLimit, DeviceSensor deviceSensorold) {

        //报警检验
        JsonSensorAlarm jsonSensorAlarm = new JsonSensorAlarm();

        if (deviceLimit.getHumidityAlarm() != null && deviceLimit.getHumidityAlarm() == 1) {
            if (deviceSensor.getHumidity() != null && deviceLimit.getHumidityMax() != null && deviceSensor.getHumidity() > deviceLimit.getHumidityMax() && deviceSensorold.getHumidity() < deviceLimit.getHumidityMax()) {
                jsonSensorAlarm.setHumidityMax(deviceSensor.getHumidity());
            }
            if (deviceSensor.getHumidity() != null && deviceLimit.getHumidityMin() != null && deviceSensor.getHumidity() < deviceLimit.getHumidityMin() && deviceSensorold.getHumidity() > deviceLimit.getHumidityMin()) {
                jsonSensorAlarm.setHumidityMin(deviceSensor.getHumidity());
            }
        }

        if (deviceLimit.getTemperatureAlarm() != null && deviceLimit.getTemperatureAlarm() == 1) {
            if (deviceSensor.getTemperature() != null && deviceLimit.getTemperatureMax() != null && deviceSensor.getTemperature() > deviceLimit.getTemperatureMax() && deviceSensorold.getTemperature() < deviceLimit.getTemperatureMax()) {
                jsonSensorAlarm.setTemperatureMax(deviceSensor.getTemperature());
            }
            if (deviceSensor.getTemperature() != null && deviceLimit.getTemperatureMin() != null && deviceSensor.getTemperature() < deviceLimit.getTemperatureMin() && deviceSensorold.getTemperature() > deviceLimit.getTemperatureMin()) {
                jsonSensorAlarm.setTemperatureMin(deviceSensor.getTemperature());
            }
        }

        if (deviceLimit.getPm25Alarm() != null && deviceLimit.getPm25Alarm() == 1) {
            if (deviceSensor.getPm25() != null && deviceLimit.getPm25Max() != null && deviceSensor.getPm25() > deviceLimit.getPm25Max() && deviceSensorold.getPm25() < deviceLimit.getPm25Max()) {
                jsonSensorAlarm.setPm25Max(deviceSensor.getPm25());
            }
            if (deviceSensor.getPm25() != null && deviceLimit.getPm25Min() != null && deviceSensor.getPm25() < deviceLimit.getPm25Min() && deviceSensorold.getPm25() > deviceLimit.getPm25Min()) {
                jsonSensorAlarm.setPm25Min(deviceSensor.getPm25());
            }
        }

        if (deviceSensor.getSmokeSensors() != null && deviceLimit.getSmokeSensorsAlarm() == 1 && deviceSensor.getSmokeSensors() == 1 && deviceSensorold.getSmokeSensors() != 1) {
            jsonSensorAlarm.setSmokeSensorsAddMessage(1);
        }

        if (deviceSensor.getBodyInductor() != null && deviceLimit.getBodyInductoAlarm() == 1 && deviceSensor.getBodyInductor() == 1 && deviceSensorold.getBodyInductor() != 1) {
            jsonSensorAlarm.setBodyInductorAddMessage(1);
        }

        if (jsonSensorAlarm.isValid()) {
            jsonSensorAlarm.setDeviceId(deviceSensor.getDeviceId());
            jsonSensorAlarm.setDeviceName(device.getDeviceName());

            return jsonSensorAlarm;
        }
        return null;

    }

    /**
     * 关闭，下线
     */
    public void close() {

    }

//    public static void main(String[] args) {
//        try {
//            String songName = "./media/audio_files/613d647891d8795115e55e9534024989.pcm";
//            String[] strs = songName.split("/");
//            String n = strs[strs.length - 1];
//            System.out.println(n);
//            String[] strs1 = n.split("\\.");
//            String s = strs1[0];
//            System.out.println(s);
//
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

}