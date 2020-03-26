package com.detection.interfaces.controller.login;

import com.commonsl.model.Entity;
import com.commonsl.model.Log;
import com.commonsl.model.User;
import com.commonsl.service.LogService;
import com.commonsl.service.UserService;
import com.commonsl.util.ErrorCode;
import com.commonsl.util.JsonResult;
import com.commonsl.util.Md5SaltTool;
import com.commonsl.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
@Controller
public class CenterController {

    @Autowired
    private UserService userService;
    @Autowired
    private LogService logService;


    @RequestMapping(value = "/getLogin", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getLogin(String userName, String userPassword, Integer loginType, HttpServletRequest request) throws Exception {
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(userName)) {
            result.addErrorCode(ErrorCode.SYS_PARAM_VALUE_ERROR);
            return result;
        }
        if (StringUtil.isBlank(userPassword)) {
            result.addErrorCode(ErrorCode.SYS_PARAM_VALUE_ERROR);
            return result;
        }
        Entity.UserCriteria criteria = new Entity.UserCriteria();
        criteria.setUserName(Entity.Value.eq(userName));
        User admin = userService.selectOne(criteria);
        if (admin == null) {
            result.addErrorCode(ErrorCode.CUSTOM_USER_PWD_ERROR);
            return result;
        } else {
            if (!Md5SaltTool.validPassword(userPassword, admin.getUserPassword())) {
                result.addErrorCode(ErrorCode.CUSTOM_USER_PWD_ERROR);
                return result;
            }
        }

        request.getSession().setAttribute("USER", admin);
        admin.setLandingTime(new Date());
        admin.setState("在线");
        userService.update(admin);

        Log log = new Log();
        log.setUserName(admin.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());
        log.setMessage("手机端登陆");
        logService.save(log);

        return result;
    }
} 