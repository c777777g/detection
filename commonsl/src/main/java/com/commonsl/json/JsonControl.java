package com.commonsl.json;

import com.commonsl.util.JsonUtil;
import net.sf.json.JSONObject;

public class JsonControl {
    String opcode;
    String deviceId;
    Double illuminatingBrightness;
    Integer flash;



    public Integer getFlash() {
        return flash;
    }

    public void setFlash(Integer flash) {
        this.flash = flash;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }



    public Double getIlluminatingBrightness() {
        return illuminatingBrightness;
    }

    public void setIlluminatingBrightness(Double illuminatingBrightness) {
        this.illuminatingBrightness = illuminatingBrightness;
    }


    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }




    public String toJson(){
        JSONObject jsonObject = JsonUtil.toJsonObject(this);
        if (illuminatingBrightness == null){
            jsonObject.remove("illuminatingBrightness");
        }
        if (opcode == null){
            jsonObject.remove("opcode");
        }
        if (deviceId == null){
            jsonObject.remove("deviceId");
        }
        if (flash == null){
            jsonObject.remove("flash");
        }

        return  JsonUtil.toJson(jsonObject);
    }
} 