package com.detection.back.controller.reportForms;

import com.commonsl.model.DeviceSensor;
import com.commonsl.model.Entity;
import com.commonsl.model.ReportForms;
import com.commonsl.service.DeviceSensorService;
import com.commonsl.service.DeviceService;
import com.commonsl.service.ReportFormsService;
import com.commonsl.util.JsonHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/reportFromMgt")
public class ReportFormsController {

   private Logger logger = Logger.getLogger(ReportFormsController.class);
   private final static SimpleDateFormat objSDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ReportFormsService reportFormsService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceSensorService deviceSensorService;

    /**
     * 显示当天的设备传感器数据
     * @return
     */
    @RequestMapping("/show")
    public String reportForms(HttpServletRequest request ,String deviceId, String time){
        String startDate = null;
        String endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (time != null && time != "" && !time.equals("NaN-NaN-NaN")) {
            try {
                String[] da1 = time.split("-");
                time = da1[0]+"-"+addZeroForNum(da1[1],2)+"-"+addZeroForNum(da1[2],2);
                Date da2 = sdf.parse(time);
                Calendar ca = Calendar.getInstance();
                ca.setTime(da2);
                startDate = sdf.format(ca.getTime());
                ca.add(Calendar.DATE, 1);
                endDate = sdf.format(ca.getTime());
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            //没有选择日期，则默认为今天
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.HOUR_OF_DAY, 00);
            calendar.set(Calendar.MINUTE, 00);
            calendar.set(Calendar.SECOND, 00);
            startDate = objSDateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE,1);
            endDate = objSDateFormat.format(calendar.getTime());
        }

        Entity.ReportFormsCriteria reportFormsCriteria = new Entity.ReportFormsCriteria();
        reportFormsCriteria.setDeviceId(Entity.Value.eq(deviceId));
        reportFormsCriteria.setCreateDate(Entity.Value.between(startDate, endDate));
        reportFormsCriteria.orderBy("create_date", Entity.By.ASC);
        List<ReportForms> reportForms = reportFormsService.selectList(reportFormsCriteria);
        //request.setAttribute("reportForms",reportForms);

        double[][] temperature = new double[24][2]; //温度
        double[][] humidity = new double[24][2]; //湿度
        double[][] pm25 = new double[24][2]; //pm2.5
        double[][] illuminating_brightness = new double[24][2];//照明亮度
        int[][] body_inductor = new int[24][2]; //人体感应
        int[][] smoke_sensors = new int[24][2]; //烟感
        double[][] optical_inductor = new double[24][2]; //环境光度

        for (int j =0; j<24; j++){
            temperature[j][0] = j;
            humidity[j][0] = j;
            pm25[j][0] = j;
            illuminating_brightness[j][0] = j;
            body_inductor[j][0] = j;
            smoke_sensors[j][0] = j;
            optical_inductor[j][0] = j;
        }

        for (ReportForms rep : reportForms){
            String createdDate = objSDateFormat.format(rep.getCreateDate());
            String hour = createdDate.substring(11,13);
            int i = Integer.parseInt(hour);
            temperature[i][1]= rep.getTemperature()==null?0:rep.getTemperature();
            humidity[i][1] = rep.getHumidity()==null?0:rep.getHumidity();
            pm25[i][1] = rep.getPm25()==null?0:rep.getPm25();
            illuminating_brightness[i][1] = rep.getIlluminatingBrightness()==null?0:rep.getIlluminatingBrightness();
            body_inductor[i][1] = rep.getBodyInductor()==null?0:rep.getBodyInductor();
            smoke_sensors[i][1] = rep.getSmokeSensors()==null?0:rep.getSmokeSensors();
            optical_inductor[i][1] = rep.getOpticalInductor()==null?0:rep.getOpticalInductor();
            i++;
        }
        String temperatures = new String();
        String humiditys = new String();
        String pm25s = new String();
        String illuminatingBrightness = new String();
        String body_inductors = new String();
        String smoke_sensorss = new String();
        String optical_inductors = new String();
        temperatures = JsonHelper.toJson(temperature);
        humiditys = JsonHelper.toJson(humidity);
        pm25s = JsonHelper.toJson(pm25);
        illuminatingBrightness = JsonHelper.toJson(illuminating_brightness);
        body_inductors = JsonHelper.toJson(body_inductor);
        smoke_sensorss = JsonHelper.toJson(smoke_sensors);
        optical_inductors = JsonHelper.toJson(optical_inductor);
        request.setAttribute("temperature", temperatures);
        request.setAttribute("humidity", humiditys);
        request.setAttribute("pm25", pm25s);
        request.setAttribute("illuminating_brightness", illuminatingBrightness);
        request.setAttribute("body_inductor", body_inductors);
        request.setAttribute("smoke_sensors", smoke_sensorss);
        request.setAttribute("optical_inductor", optical_inductors);
        request.setAttribute("deviceId", deviceId);
        request.setAttribute("DATE", startDate.substring(0,10));
        return "reportForms/reportForms";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(HttpServletResponse response, HttpServletRequest request, Entity.Pagination pagination) {
        Entity.DeviceCriteria deviceCriteria = new Entity.DeviceCriteria();
        pagination = deviceService.selectPage(deviceCriteria, pagination);
        if (pagination.getPage() == 0) {
            pagination.setPage(1);
        }
        request.setAttribute("pagination", pagination);
        return "reportForms/grading";
    }

    /**
     * 批量保存传感器报警数据
     * @return
     */
    @RequestMapping("recordDeviceSensor")
    public void recordDeviceSensor(){
        String startDate;
        String endDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        startDate = objSDateFormat.format(calendar.getTime());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        endDate = objSDateFormat.format(calendar.getTime());

        List<ReportForms> reportFormsList2 = new ArrayList<>();
        Entity.ReportFormsCriteria reportFormsCriteria = new Entity.ReportFormsCriteria();
        reportFormsCriteria.setCreateDate(Entity.Value.between(startDate, endDate));
        List<ReportForms> reportFormsList = reportFormsService.selectList(reportFormsCriteria);

        List<DeviceSensor> deviceSensorList = deviceSensorService.selectAll();
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
            reportFormsList2.add(reportForms1);
        }
        if (reportFormsList.size()>0){
            //修改
            reportFormsService.batchUpdate(reportFormsList2, startDate, endDate);
            logger.info("recordDeviceSensor批量修改成功");
        }else{
            //保存
            reportFormsService.saveAll(reportFormsList2);
            logger.info("recordDeviceSensor批量保存成功");
        }
    }

    /**
     * 字符串前面补0
     * @param str
     * @param strLength
     * @return
     */
    public String addZeroForNum(String str, int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);//左补0
                // sb.append(str).append("0");//右补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

}
