package com.commonsl.util;

import com.commonsl.model.Device;
import com.commonsl.model.DeviceRecord;
import com.commonsl.model.DeviceSensor;
import com.commonsl.service.DeviceRecordService;
import com.commonsl.service.DeviceSensorService;
import com.commonsl.service.DeviceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceRecordTask {
    private Logger logger = Logger.getLogger(ReportFormTask.class);

    @Autowired
    private DeviceSensorService deviceSensorService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRecordService deviceRecordService;

    public void saveDeviceRecord(){
        List<Device> deviceList = deviceService.selectAll();
        List<DeviceSensor> deviceSensorList = deviceSensorService.selectAll();
        List<DeviceRecord> deviceRecords = new ArrayList<>();

        for (Device device : deviceList) {
            for (DeviceSensor deviceSensor : deviceSensorList) {
                if (device.getDeviceId().equals(deviceSensor.getDeviceId())) {
                    DeviceRecord deviceRecord = new DeviceRecord();
                    deviceRecord.setDeviceId(device.getDeviceId());
                    deviceRecord.setDeviceName(device.getDeviceName());
                    deviceRecord.setDeviceAddress(device.getDeviceAddress());
                    deviceRecord.setLatlng(deviceSensor.getLatlng());
                    deviceRecord.setState(device.getState());
                    deviceRecord.setCreateTime(new Date());
                    deviceRecords.add(deviceRecord);
                }
            }
        }
        deviceRecordService.batchSave(deviceRecords);
    }

}
