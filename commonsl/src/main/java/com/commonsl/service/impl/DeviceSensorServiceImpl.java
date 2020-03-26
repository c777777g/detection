package com.commonsl.service.impl;

import com.commonsl.dao.DeviceSensorDao;
import com.commonsl.model.DeviceSensor;
import com.commonsl.service.DeviceSensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceSensorServiceImpl extends BaseServiceImpl<Integer, DeviceSensor> implements DeviceSensorService {
	
	@Autowired
	private DeviceSensorDao dao;

}