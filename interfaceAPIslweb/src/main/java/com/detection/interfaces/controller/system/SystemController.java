package com.detection.interfaces.controller.system;

import com.commonsl.util.CommandUtil;
import com.commonsl.util.JsonResult;
import com.commonsl.util.SigarUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


@RequestMapping("/systemMgt")
@Controller
public class SystemController {
    private Logger logger = Logger.getLogger(SystemController.class);

    @RequestMapping(value = "/changeIP", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult changeIP(HttpServletRequest request, String address,String gateway,String netmask ,String dns) {

        JsonResult jsonResult = new JsonResult();
        String s = CommandUtil.changeIP(address,gateway,netmask,dns);
        jsonResult.setData(s);
        return jsonResult;
    }

    @RequestMapping(value = "/redoot", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult redoot(HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult();
        String s = CommandUtil.rebootLinux();
        jsonResult.setData(s);
        return jsonResult;
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult test(HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult();

        try {

            // System信息，从jvm获取
           SigarUtil.property();
            System.out.println("----------------------------------");
            // cpu信息
            SigarUtil.cpu();
            System.out.println("----------------------------------");
            // 内存信息
            SigarUtil.memory();
            System.out.println("----------------------------------");
            // 操作系统信息
            SigarUtil.os();
            System.out.println("----------------------------------");
            // 用户信息
            SigarUtil.who();
            System.out.println("----------------------------------");
            // 文件系统信息
            SigarUtil.file();
            System.out.println("----------------------------------");
            // 网络信息
            SigarUtil.net();
            System.out.println("----------------------------------");
            // 以太网信息
            SigarUtil.ethernet();
            System.out.println("----------------------------------");
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        return jsonResult;
    }


}
