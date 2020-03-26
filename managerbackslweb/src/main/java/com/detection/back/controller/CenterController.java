package com.detection.back.controller;

import com.commonsl.model.Entity;
import com.commonsl.model.Log;
import com.commonsl.model.User;
import com.commonsl.service.LogService;
import com.commonsl.service.MenuService;
import com.commonsl.service.UserService;
import com.commonsl.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Controller
public class CenterController {
    private static final Logger LOG = LoggerFactory.getLogger(CenterController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private Redis redis;


    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String main(HttpServletRequest request, String loginId) {
        User u = (User) request.getSession().getAttribute("USER");

        if (u != null) {
            request.getSession().setAttribute("USER", u);
            request.setAttribute("USER", u);
            redis.setex(String.format(RedisKey.LOGIN, loginId), JsonHelper.toJson(u), 15 * 60);
            if (u.getRole().equals("user")) {
                request.setAttribute("menus", menuService.getMenuListByAdmin(1));
                return "login/home";
            } else if (u.getRole().equals("admin")) {
                request.setAttribute("menus", menuService.getMenuListByAdmin(2));
                return "login/home";
            } else if (u.getRole().equals("manage")) {
                request.setAttribute("menus", menuService.getMenuListByAdmin(4));
                return "login/home";
            }
        }
        return "login/login";

    }

    @RequestMapping(value = "/getLogin", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult getLogin(String userName, String userPassword, Integer loginType, HttpServletRequest request) throws Exception {
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
        log.setMessage("登陆");
        logService.save(log);

        return result;
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome(HttpServletRequest request) {
        User u = (User) request.getSession().getAttribute("USER");
        if (u != null) {
            request.getSession().invalidate();
        }
        return "login/login";

    }


    @RequestMapping(value = "/emailLogin", method = RequestMethod.GET)
    public String emailLogin(HttpServletRequest request, Integer loginType, String num) {
        String s = redis.getString(String.format(RedisKey.FINDPASSWORD, num));
        if (s == null) {
            return "login/login";
        }

        User user = JsonHelper.fromJson(s, User.class);
        request.getSession().setAttribute("USER", user);

        return main(request, request.getSession().getId());
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePassword(HttpServletRequest request, Integer loginType, String num) {
        String key = String.format(RedisKey.FINDPASSWORD, num);
        String s = redis.getString(key);
        redis.del(key);
        LOG.info("收到的是：" + s);
        if (s == null) {
            return "login/login";
        }

        User user = JsonHelper.fromJson(s, User.class);
        request.getSession().setAttribute("USER", user);
        request.getSession().setAttribute("LOGINTYPE", loginType);
        LOG.info("dasddas" + request.getSession().getAttribute("USER"));

        return "login/changePassword";
    }
}
