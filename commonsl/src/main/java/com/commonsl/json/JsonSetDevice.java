package com.commonsl.json;

import com.commonsl.util.JsonUtil;
import net.sf.json.JSONObject;

public class JsonSetDevice {

    String opcode;
    String deviceId;
    Integer volume;
    String IPAddress;
    String subnetMask;
    String gateway;
    Integer insideAmplifier;
    Integer outerAmplifier;



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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public Integer getInsideAmplifier() {
        return insideAmplifier;
    }

    public void setInsideAmplifier(Integer insideAmplifier) {
        this.insideAmplifier = insideAmplifier;
    }

    public Integer getOuterAmplifier() {
        return outerAmplifier;
    }

    public void setOuterAmplifier(Integer outerAmplifier) {
        this.outerAmplifier = outerAmplifier;
    }

    public String toJson(){
        JSONObject jsonObject = JsonUtil.toJsonObject(this);
        if (volume == null){
            jsonObject.remove("volume");
        }
        if (IPAddress == null){
            jsonObject.remove("IPAddress");
        }
        if (subnetMask == null){
            jsonObject.remove("subnetMask");
        }
        if (opcode == null){
            jsonObject.remove("opcode");
        }
        if (deviceId == null){
            jsonObject.remove("deviceId");
        }
        if (gateway == null){
            jsonObject.remove("gateway");
        }
        if (insideAmplifier == null){
            jsonObject.remove("insideAmplifier");
        }
        if (outerAmplifier == null){
            jsonObject.remove("outerAmplifier");
        }
        return  JsonUtil.toJson(jsonObject);
    }
}