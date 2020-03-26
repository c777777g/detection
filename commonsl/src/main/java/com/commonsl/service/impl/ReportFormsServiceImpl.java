package com.commonsl.service.impl;

import com.commonsl.dao.ReportFormsDao;
import com.commonsl.dao.impl.ReportFormsDaoImpl2;
import com.commonsl.model.DeviceSensor;
import com.commonsl.model.Entity;
import com.commonsl.model.ReportForms;
import com.commonsl.service.DeviceSensorService;
import com.commonsl.service.DeviceService;
import com.commonsl.service.ReportFormsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReportFormsServiceImpl extends BaseServiceImpl<Integer, ReportForms> implements ReportFormsService {


	@Autowired
	private ReportFormsDaoImpl2 dao;

	@Override
	public void saveAll(List<ReportForms> reportForms) {
		dao.saveAll(reportForms);
	}

	@Override
	public void batchUpdate(List<ReportForms> reportForms, String startDate, String endDate) {
		dao.batchUpdate(reportForms, startDate, endDate);
	}
}