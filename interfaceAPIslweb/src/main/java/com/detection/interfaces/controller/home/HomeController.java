package com.detection.interfaces.controller.home;

import com.commonsl.model.Entity;
import com.commonsl.model.User;
import com.commonsl.service.UserService;
import com.commonsl.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/homeMgt")
@Controller
public class HomeController {
    private Logger logger = Logger.getLogger(HomeController.class);

    @Autowired
    private Redis redis;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult getLogin(String passport, String passwd, HttpServletRequest request) throws Exception {
        Map<String, Object> map2 = new HashMap<String, Object>();
        JsonResult result = new JsonResult();
        if (StringUtil.isBlank(passport)) {
            result.addErrorCode(ErrorCode.SYS_PARAM_VALUE_ERROR);
            return result;
        }
        if (StringUtil.isBlank(passwd)) {
            result.addErrorCode(ErrorCode.SYS_PARAM_VALUE_ERROR);
            return result;
        }
        /*判断登录管理人员*/
        Entity.UserCriteria userCriteria = new Entity.UserCriteria();
        userCriteria.setUserName(Entity.Value.eq(passport));
        User user = userService.selectOne(userCriteria);
        if (user != null) {
            if (!Md5SaltTool.validPassword(passwd, user.getUserPassword())) {
                result.addErrorCode(ErrorCode.CUSTOM_USER_PWD_ERROR);
            }
        } else {
            result.addErrorCode(ErrorCode.CUSTOM_USER_PWD_ERROR);
        }
        if (result.getStatus() == 1) {
            user.setLandingTime(new Date());
            userService.update(user);
            result.addErrorCode(ErrorCode.SYS_SUSSES);
            result.setStatus(1);
            request.getSession().setAttribute("USER", user);
            map2.put("role", user.getRole());
        }

        map2.put("sessionID", request.getSession().getId());
        result.setData(map2);
        logger.info("getSession++++++++++++++++:" + request.getSession().getId());
        return result;
    }



}