package com.commonsl.service.impl;

import com.commonsl.dao.LogDao;
import com.commonsl.model.Log;
import com.commonsl.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl extends BaseServiceImpl<Integer, Log> implements LogService {
	
	@Autowired
	private LogDao dao;

}