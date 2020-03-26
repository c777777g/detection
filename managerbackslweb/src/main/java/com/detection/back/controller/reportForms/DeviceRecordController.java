package com.detection.back.controller.reportForms;

import com.commonsl.model.DeviceRecord;
import com.commonsl.model.Entity;
import com.commonsl.service.DeviceRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/deviceRecordMgt")
public class DeviceRecordController {

    private Logger logger = Logger.getLogger(DeviceRecordController.class);
    private final static SimpleDateFormat objSDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private DeviceRecordService deviceRecordService;

    @RequestMapping("/deviceRecord")
    public String deviceRecord(HttpServletRequest request, String time, String selectType, String selectKey){

        String startDate = null;
        String endDate = null;

        if (time != null && time != "" && !time.equals("NaN-NaN-NaN")){
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(objSDateFormat.parse(time));
                int minute = calendar.get(Calendar.MINUTE);
                if (0 <= minute && minute < 30){//0-29
                    calendar.set(Calendar.MINUTE, 00);
                    calendar.set(Calendar.SECOND, 00);
                    startDate = objSDateFormat.format(calendar.getTime());
                    calendar.set(Calendar.MINUTE, 29);
                    calendar.set(Calendar.SECOND, 59);
                    endDate = objSDateFormat.format(calendar.getTime());
                }else {//30-59
                    calendar.set(Calendar.MINUTE, 30);
                    calendar.set(Calendar.SECOND, 00);
                    startDate = objSDateFormat.format(calendar.getTime());
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endDate = objSDateFormat.format(calendar.getTime());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            //没有选择日期，则默认为今天当前最新时间
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int minute = calendar.get(Calendar.MINUTE);

            if (0 <= minute && minute < 30){//0-29
                calendar.set(Calendar.MINUTE, 00);
                calendar.set(Calendar.SECOND, 00);
                startDate = objSDateFormat.format(calendar.getTime());
                calendar.set(Calendar.MINUTE, 29);
                calendar.set(Calendar.SECOND, 59);
                endDate = objSDateFormat.format(calendar.getTime());
            }else { //30-59
                calendar.set(Calendar.MINUTE, 30);
                calendar.set(Calendar.SECOND, 00);
                startDate = objSDateFormat.format(calendar.getTime());
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                endDate = objSDateFormat.format(calendar.getTime());
            }
        }

        Entity.DeviceRecordCriteria deviceRecordCriteria = new Entity.DeviceRecordCriteria();
        deviceRecordCriteria.setCreateTime(Entity.Value.between(startDate, endDate));
        if (selectKey != null && selectKey != ""){
            if ("1".equals(selectType)){
                //设备ID查询
                //deviceRecordCriteria.setDeviceId(Entity.Value.eq(selectKey));
                deviceRecordCriteria.like("device_id", "%"+selectKey+"%");
            }else if ("2".equals(selectType)){
                //设备名查询
                //deviceRecordCriteria.setDeviceName(Entity.Value.eq(selectKey));
                deviceRecordCriteria.like("device_name","%"+selectKey+"%");
            }else {
                logger.info("查询设备位置记录类型异常");
            }
            deviceRecordCriteria.orderBy("create_time", Entity.By.DESC);
            //deviceRecordCriteria.limit(1,1);
        }
        List<DeviceRecord> deviceRecords = deviceRecordService.selectList(deviceRecordCriteria);

        String data = null;
        for (DeviceRecord dr : deviceRecords){
            if (dr.getLatlng() != null && !"".equals(dr.getLatlng())) {
                String state = "在线";
                if (dr.getState() == 1) {
                    state = "在线";
                } else {
                    state = "离线";
                }
                if (data != null)
                    data += dr.getLatlng().replace("]", ",\"") + "设备名:" + dr.getDeviceName() + "<br />地址:" + dr.getDeviceAddress() + "<br />状态:" + state + "\"]" + ",";
                else
                    data = dr.getLatlng().replace("]", ",\"") + "设备名:" + dr.getDeviceName() + "<br />地址:" + dr.getDeviceAddress() + "<br />状态:" + state + "\"]" + ",";
            }
        }
        request.setAttribute("MARKER", data);
        return "reportForms/deviceRecord";
    }

}
