package com.commonsl.service.impl;

import com.commonsl.dao.impl.DeviceRecordDaoImpl2;
import com.commonsl.model.DeviceRecord;
import com.commonsl.service.DeviceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceRecordServiceImpl extends BaseServiceImpl<Integer, DeviceRecord> implements DeviceRecordService {
	
	@Autowired
	private DeviceRecordDaoImpl2 dao;

	public void batchSave(List<DeviceRecord> deviceRecords){
		dao.batchSave(deviceRecords);
	}

}