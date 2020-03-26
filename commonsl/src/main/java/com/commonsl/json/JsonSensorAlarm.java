package com.commonsl.json;

public class JsonSensorAlarm {

    protected boolean valid = false;
    protected String opcode = ProtocolKey.sensorAlarm;
    protected String deviceId;
    protected String deviceName;

    /**  */
    protected Double humidityMax;

    /**  */
    protected Double humidityMin;

    /**  */
    protected Double pm25Max;

    /**  */
    protected Double pm25Min;

    /**  */
    protected Double temperatureMax;

    /**  */
    protected Double temperatureMin;


    protected String alarmMessage = "";


    protected int smokeSensors ;

    protected int bodyInductor ;

    protected int state ;


    public boolean isValid() {
        if (alarmMessage.length() > 4) {
            valid = true;
        }
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getAlarmMessage() {
        return alarmMessage;
    }

    public void setAlarmMessage(String alarmMessage) {
        this.alarmMessage = alarmMessage;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int isSmokeSensors() {
        return smokeSensors;
    }

    public void setSmokeSensorsAddMessage(int smokeSensors) {
        if (smokeSensors ==1) {
            alarmMessage += "触发烟雾报警;";
        }
        this.smokeSensors = smokeSensors;
    }

    public int isBodyInductor() {
        return bodyInductor;
    }

    public void setBodyInductorAddMessage(int bodyInductor) {
        if (bodyInductor ==1) {
            alarmMessage += "触发人体报警;";
        }
        this.bodyInductor = bodyInductor;
    }


    public Double getHumidityMax() {
        return humidityMax;
    }

    public void setHumidityMax(Double humidityMax) {
        alarmMessage += "湿度高达" + humidityMax + ";";
        this.humidityMax = humidityMax;
    }

    public Double getHumidityMin() {
        return humidityMin;
    }

    public void setHumidityMin(Double humidityMin) {
        alarmMessage += "湿度低达" + humidityMin + ";";
        this.humidityMin = humidityMin;
    }

    public Double getPm25Max() {
        return pm25Max;
    }

    public void setPm25Max(Double pm25Max) {
        alarmMessage += "PM2.5高达" + pm25Max + ";";
        this.pm25Max = pm25Max;
    }

    public Double getPm25Min() {
        return pm25Min;
    }

    public void setPm25Min(Double pm25Min) {
        alarmMessage += "PM2.5低达" + pm25Min + ";";
        this.pm25Min = pm25Min;
    }

    public Double getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(Double temperatureMax) {
        alarmMessage += "温度高达" + temperatureMax + ";";
        this.temperatureMax = temperatureMax;
    }

    public Double getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(Double temperatureMin) {
        alarmMessage += "温度低达" + temperatureMin + ";";
        this.temperatureMin = temperatureMin;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public int getState() {
        return state;
    }

    public int getSmokeSensors() {
        return smokeSensors;
    }

    public int getBodyInductor() {
        return bodyInductor;
    }

    public void setSmokeSensors(int smokeSensors) {
        this.smokeSensors = smokeSensors;
    }

    public void setBodyInductor(int bodyInductor) {
        this.bodyInductor = bodyInductor;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setStateAddMessage(int state) {
        if (state == 1) {
            alarmMessage += "设备上线;";
        }else if(state == 2){
            alarmMessage += "设备离线;";
        }
        this.state = state;
    }
}