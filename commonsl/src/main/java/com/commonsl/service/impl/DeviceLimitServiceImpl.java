package com.commonsl.service.impl;

import com.commonsl.dao.DeviceLimitDao;
import com.commonsl.model.DeviceLimit;
import com.commonsl.service.DeviceLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceLimitServiceImpl extends BaseServiceImpl<Integer, DeviceLimit> implements DeviceLimitService {
	
	@Autowired
	private DeviceLimitDao dao;

}