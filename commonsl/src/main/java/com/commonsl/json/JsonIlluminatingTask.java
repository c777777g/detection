package com.commonsl.json;

import com.commonsl.model.DeviceIlluminatingTask;

import java.util.ArrayList;
import java.util.List;

public class JsonIlluminatingTask {
    String opcode  = ProtocolKey.illuminatingTask;
    String deviceId;
    String date;
    List<DeviceIlluminatingTask> illuminatingTasks = new ArrayList<>();
    public List<DeviceIlluminatingTask> getIlluminatingTasks() {
        return illuminatingTasks;
    }

    public void setIlluminatingTasks(List<DeviceIlluminatingTask> illuminatingTasks) {
        this.illuminatingTasks = illuminatingTasks;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}