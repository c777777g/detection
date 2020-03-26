package com.commonsl.json;

public class JsonReportedData {
    String opcode;
    String deviceId;
    Double temperature;
    Double pm25;
    Double humidity;
    Double opticalInductor;
    Double illuminatingBrightness;
    String latlng;
    int bodyInductor;
    int smokeSensors;
    Integer volume;
    Double fm_status;
    int play_status;
    String song_name;
    int mode;
    int insideAmplifier;
    int outerAmplifier;
    int ka;

    public Double getFm_status() {
        return fm_status;
    }

    public void setFm_status(Double fm_status) {
        this.fm_status = fm_status;
    }

    public int getPlay_status() {
        return play_status;
    }

    public void setPlay_status(int play_status) {
        this.play_status = play_status;
    }

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    public int getSmokeSensors() {
        return smokeSensors;
    }

    public void setSmokeSensors(int smokeSensors) {
        this.smokeSensors = smokeSensors;
    }

    public int getBodyInductor() {
        return bodyInductor;
    }

    public void setBodyInductor(int bodyInductor) {
        this.bodyInductor = bodyInductor;
    }

    public Double getPm25() {
        return pm25;
    }

    public void setPm25(Double pm25) {
        this.pm25 = pm25;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public Double getIlluminatingBrightness() {
        return illuminatingBrightness;
    }

    public void setIlluminatingBrightness(Double illuminatingBrightness) {
        this.illuminatingBrightness = illuminatingBrightness;
    }

    public Double getOpticalInductor() {
        return opticalInductor;
    }

    public void setOpticalInductor(Double opticalInductor) {
        this.opticalInductor = opticalInductor;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }


    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public String getOpcode() {
        return opcode;
    }

    public void setOpcode(String opcode) {
        this.opcode = opcode;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public int getKa() {
        return ka;
    }

    public void setKa(int ka) {
        this.ka = ka;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getInsideAmplifier() {
        return insideAmplifier;
    }

    public void setInsideAmplifier(int insideAmplifier) {
        this.insideAmplifier = insideAmplifier;
    }

    public int getOuterAmplifier() {
        return outerAmplifier;
    }

    public void setOuterAmplifier(int outerAmplifier) {
        this.outerAmplifier = outerAmplifier;
    }
}