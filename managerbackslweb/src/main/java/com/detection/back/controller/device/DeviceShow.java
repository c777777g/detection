package com.detection.back.controller.device;

import com.commonsl.model.Device;
import com.commonsl.model.DeviceSensor;
import org.opencv.video.Video;

import java.util.Date;
import java.util.List;

public class DeviceShow {
    /**  */
    protected String companyName;

    /**  */
    protected Date createTime;

    /**  */
    protected String deviceAddress;

    /**  */
    protected String deviceId;

    /**  */
    protected String deviceName;

    /**  */
    protected String district;

    /**  */
    protected String ip;

    /**  */
    protected Integer state;

    /**  */
    protected String userName;


    protected DeviceSensor deviceSensor;

    protected List<Video> videoList;


    public DeviceShow(Device device , DeviceSensor deviceSensor , List<Video> videoList){
        companyName = device.getCompanyName();
        createTime = device.getCreateTime();
        deviceAddress = device.getDeviceAddress();
        deviceId = device.getDeviceId();
        deviceName = device.getDeviceName();
        district = device.getDistrict();
        ip = device.getIp();
        state = device.getState();
        userName = device.getUserName();
        this.deviceSensor = deviceSensor;
        this.videoList = videoList;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public DeviceSensor getDeviceSensor() {
        return deviceSensor;
    }

    public void setDeviceSensor(DeviceSensor deviceSensor) {
        this.deviceSensor = deviceSensor;
    }

    public List<Video> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<Video> videoList) {
        this.videoList = videoList;
    }
}