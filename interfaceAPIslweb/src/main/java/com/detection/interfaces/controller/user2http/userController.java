package com.detection.interfaces.controller.user2http;

import com.commonsl.model.Entity;
import com.commonsl.model.Log;
import com.commonsl.model.User;
import com.commonsl.service.LogService;
import com.commonsl.service.UserService;
import com.commonsl.util.ErrorCode;
import com.commonsl.util.JsonHelper;
import com.commonsl.util.JsonResult;
import com.commonsl.util.Md5SaltTool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RequestMapping("/userMgt")
@Controller
public class userController {
    private Logger logger = Logger.getLogger(userController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    @RequestMapping(value = "/list")
    public String list(HttpServletRequest request, Entity.Pagination pagination) {
        pagination.setSort("user_name");
        pagination.setOrder("DESC");
        Entity.UserCriteria userCriteria = new Entity.UserCriteria();
        userCriteria.setRole(Entity.Value.eq("user"));
        pagination = userService.selectPage(userCriteria, pagination);
        request.setAttribute("pagination", pagination);
        return "user/user_list";
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonResult add(HttpServletRequest request, User user) {
        User admin = (User) request.getSession().getAttribute("USER");
        logger.info("user是:"+ JsonHelper.toJson(user));
        JsonResult jsonResult = new JsonResult();
        if (user.getUserName() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "账号不能为空"));
            return jsonResult;
        }
        if (user.getPhone() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "电话号码不能为空"));
            return jsonResult;
        }
        if (user.getAlias() == null) {
            jsonResult.addErrorCode(new ErrorCode(100003, "别名不能为空"));
            return jsonResult;
        }
        try {
            Entity.UserCriteria userCriteria = new Entity.UserCriteria();
            userCriteria.setUserName(Entity.Value.eq(user.getUserName()));
            User u = userService.selectOne(userCriteria);
            if (u != null) {
                jsonResult.addErrorCode(new ErrorCode(100003, "账号不能重复"));
                return jsonResult;
            }

//            user.setId(CommonUtil.createUserCode());
            user.setRole("user");
            user.setCreationTime(new Date());
            user.setState("注册未登录");
            user.setLandingTime(new Date());
            user.setUserPassword(Md5SaltTool.getEncryptedPwd("123456"));
            userService.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.addErrorCode(new ErrorCode(100003, "未知错误"));
        }

        Log log = new Log();
        log.setUserName(admin.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("创建账号：" + user.getUserName());
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult del(HttpServletRequest request, Integer id) {
        User admin = (User) request.getSession().getAttribute("USER");
        JsonResult jsonResult = new JsonResult();
        User user = userService.selectOne(id);
        userService.delete(id);
        Log log = new Log();
        log.setUserName(admin.getUserName());
        log.setType(4);
        log.setCreateTime(new Date());

        log.setMessage("创建账号：" + user.getUserName());
        logService.save(log);
        return jsonResult;
    }

    @RequestMapping(value = "/updatePwd", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult updatePwd(HttpServletRequest request, String newPwd, String newPwd2) {
        User admin = (User) request.getSession().getAttribute("USER");
        JsonResult result = new JsonResult();
        if ( newPwd == null || newPwd2 == null) {
            result.addErrorCode(new ErrorCode(100003, "参数不能为空"));
            return result;
        }
        if (!newPwd.equals(newPwd2)) {
            result.addErrorCode(new ErrorCode(100003, "两次新密码输入不一致"));
            return result;
        }

        if (admin == null) {
            result.addErrorCode(new ErrorCode(100003, "未登录"));
            return result;
        } else {
            try {
                    admin.setUserPassword(Md5SaltTool.getEncryptedPwd(newPwd));
                    userService.update(admin);

                    Log log = new Log();
                    log.setUserName(admin.getUserName());
                    log.setType(4);
                    log.setCreateTime(new Date());

                    log.setMessage("修改密码");
                    logService.save(log);

            } catch (Exception e) {
                e.printStackTrace();
                result.addErrorCode(new ErrorCode(100002, "系统错误"));
                return result;
            }
        }
        return result;
    }

    @RequestMapping(value = "/log")
    public String log(HttpServletRequest request, Entity.Pagination pagination) {
        pagination.setSort("create_time");
        pagination.setOrder("DESC");
        pagination.setLimit(20);
        Entity.LogCriteria logCriteria = new Entity.LogCriteria();
        pagination = logService.selectPage(logCriteria, pagination);
        request.setAttribute("pagination", pagination);
        return "user/deviceLog_list";
    }

}