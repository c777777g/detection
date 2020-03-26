package com.detection.back.controller.system;

import com.commonsl.util.CommandUtil;
import com.commonsl.util.JsonResult;
import com.commonsl.util.SigarUtil;
import org.apache.log4j.Logger;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
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

    @RequestMapping(value = "/system", method = RequestMethod.GET)
    public String list( HttpServletRequest request) {
//
        try {
            NetInterfaceConfig cfg = SigarUtil.ethernet();
            request.setAttribute("address", cfg.getAddress());
            request.setAttribute("netmask", cfg.getNetmask());

            NetInfo netInfo = SigarUtil.netInfo();
            request.setAttribute("dns", netInfo.getPrimaryDns());
            request.setAttribute("gateway", netInfo.getDefaultGateway());

            request.setAttribute("cpu", SigarUtil.cpu());
            request.setAttribute("memory", SigarUtil.memory());

        }catch (Exception e){
            logger.info("111111111111111111111111111111111111111111111111111111111111111111111111");
            e.printStackTrace();
            request.setAttribute("address","获取失败");
            request.setAttribute("netmask", "获取失败");

            request.setAttribute("dns","获取失败");
            request.setAttribute("gateway", "获取失败");

            request.setAttribute("cpu","获取失败");
            request.setAttribute("memory", "获取失败");
        }

        return "system/system_setting";
    }


    @RequestMapping(value = "/redoot", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult redoot(HttpServletRequest request) {

        JsonResult jsonResult = new JsonResult();
        String s = CommandUtil.rebootLinux();
        jsonResult.setData(s);
        return jsonResult;
    }


}
