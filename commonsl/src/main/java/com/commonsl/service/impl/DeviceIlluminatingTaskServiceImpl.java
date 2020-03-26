package com.commonsl.service.impl;

import com.commonsl.dao.DeviceIlluminatingTaskDao;
import com.commonsl.model.DeviceIlluminatingTask;
import com.commonsl.service.DeviceIlluminatingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceIlluminatingTaskServiceImpl extends BaseServiceImpl<Integer, DeviceIlluminatingTask> implements DeviceIlluminatingTaskService {

	@Autowired
	private DeviceIlluminatingTaskDao dao;

}