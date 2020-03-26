package com.commonsl.util;

import com.commonsl.model.DeviceSensor;
import com.commonsl.model.Entity;
import com.commonsl.model.ReportForms;
import com.commonsl.service.DeviceSensorService;
import com.commonsl.service.ReportFormsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *  报表任务定时器
 */
public class ReportFormTask {
    private Logger logger = Logger.getLogger(ReportFormTask.class);

    @Autowired
    private DeviceSensorService deviceSensorService;

    @Autowired
    private ReportFormsService reportFormsService;


    /**
     * 批量保存传感器数据
     * @return
     */
    public void saveReportForms(){
        SimpleDateFormat objSDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String startDate;
        String endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        startDate = objSDateFormat.format(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        endDate = objSDateFormat.format(calendar.getTime());
        Entity.ReportFormsCriteria reportFormsCriteria = new Entity.ReportFormsCriteria();
        reportFormsCriteria.setCreateDate(Entity.Value.between(startDate, endDate));
        //System.out.println("adate:"+startDate+"     :"+endDate);
        List<ReportForms> reportFormsList = reportFormsService.selectList(reportFormsCriteria);
        if (reportFormsList.size() > 0){//有数据，说明在个这小时保存了报警的数据， 在这就不做保留数据操作
            logger.info("saveReportForms 这个小时内已经保存过报警数据");
            return;
        }

        List<DeviceSensor> deviceSensorList = deviceSensorService.selectAll();
        List<ReportForms> reportForms = new ArrayList<>();

        for (DeviceSensor deviceSensor: deviceSensorList){
            ReportForms reportForms1 = new ReportForms();
            reportForms1.setHumidity(deviceSensor.getHumidity());
            reportForms1.setTemperature(deviceSensor.getTemperature());
            reportForms1.setBodyInductor(deviceSensor.getBodyInductor());
            reportForms1.setCreateDate(new Date());
            reportForms1.setDeviceId(deviceSensor.getDeviceId());
            reportForms1.setIlluminatingBrightness(deviceSensor.getIlluminatingBrightness());
            reportForms1.setOpticalInductor(deviceSensor.getOpticalInductor());
            reportForms1.setPm25(deviceSensor.getPm25());
            reportForms1.setSmokeSensors(deviceSensor.getSmokeSensors());
            reportForms.add(reportForms1);
        }

        if (deviceSensorList.size()>0){
            reportFormsService.saveAll(reportForms);
        }
    }
}
