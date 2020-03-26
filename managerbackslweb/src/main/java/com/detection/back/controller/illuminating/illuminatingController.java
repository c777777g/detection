package com.detection.back.controller.illuminating;

import com.commonsl.model.*;
import com.commonsl.service.*;
import com.commonsl.util.ErrorCode;
import com.commonsl.util.JsonResult;
import com.commonsl.util.Redis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

import static com.commonsl.json.ProtocolKey.audioTask;

@RequestMapping("/illuminatingMgt")
@Controller
public class illuminatingController {
    private Logger logger = Logger.getLogger(illuminatingController.class);
    @Autowired
    private IlluminatingTaskService illuminatingTaskService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private Redis redis;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request) {

        List<IlluminatingTask> illuminatingTaskList = illuminatingTaskService.selectAll();
        request.setAttribute("illuminatingTaskList", illuminatingTaskList);

        User u = (User) request.getSession().getAttribute("USER");
        return "illuminating/illuminating_task";
    }

    @RequestMapping("/saveTask")
    @ResponseBody
    public JsonResult add(HttpServletRequest request, IlluminatingTask illuminatingTask) {

        User u = (User) request.getSession().getAttribute("USER");
        logger.info("audioTask:" + audioTask.toString());
        JsonResult jsonResult = new JsonResult();
//        illuminatingTask.setId(CommonUtil.createUserCode());

        if (illuminatingTask.getIlluminatingTaskName() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "名称不能为空"));
            return jsonResult;
        }
        if (illuminatingTask.getPlayDate() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "模式不能为空"));
            return jsonResult;
        }
        if (illuminatingTask.getPlayDate() == null && illuminatingTask.getPlayDate() != 1) {
            jsonResult.addErrorCode(new ErrorCode(100003, "该模式时间不能为空"));
            return jsonResult;
        }


        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("创建灯控任务：" + illuminatingTask.getIlluminatingTaskName());
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping("/open")
    @ResponseBody
    public JsonResult open(HttpServletRequest request, Integer id) {
        JsonResult jsonResult = new JsonResult();
        User u = (User) request.getSession().getAttribute("USER");
        IlluminatingTask illuminatingTask = illuminatingTaskService.selectOne(id);
        if (illuminatingTask == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "该任务已经不存在"));
            return jsonResult;
        }

        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("启动灯控任务：" + illuminatingTask.getIlluminatingTaskName());
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult close(HttpServletRequest request, Integer id) {
        JsonResult jsonResult = new JsonResult();
        User u = (User) request.getSession().getAttribute("USER");
        IlluminatingTask illuminatingTask = illuminatingTaskService.selectOne(id);
        if (illuminatingTask == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "该任务已经不存在"));
            return jsonResult;
        }
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("停止灯控任务：" + illuminatingTask.getIlluminatingTaskName());
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(HttpServletRequest request, Integer id) {
        User u = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();

        IlluminatingTask illuminatingTask = illuminatingTaskService.selectOne(id);
        Log log = new Log();
        log.setUserName(u.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("删除灯控任务：" + illuminatingTask.getIlluminatingTaskName());
        logService.save(log);
        return jsonResult;
    }
} 