package com.commonsl.service.impl;

import com.commonsl.dao.IlluminatingTaskDao;
import com.commonsl.model.IlluminatingTask;
import com.commonsl.service.IlluminatingTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IlluminatingTaskServiceImpl extends BaseServiceImpl<Integer, IlluminatingTask> implements IlluminatingTaskService {
	
	@Autowired
	private IlluminatingTaskDao dao;

}