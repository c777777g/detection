package com.commonsl.service;

import com.commonsl.model.DeviceRecord;

import java.util.List;

public interface DeviceRecordService extends BaseService<Integer, DeviceRecord> {

    public void batchSave(List<DeviceRecord> deviceRecords);

}