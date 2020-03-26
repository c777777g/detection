package com.commonsl.service.impl;

import com.commonsl.dao.DeviceDao;
import com.commonsl.model.Device;
import com.commonsl.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl extends BaseServiceImpl<Integer, Device> implements DeviceService {
	
	@Autowired
	private DeviceDao dao;

}