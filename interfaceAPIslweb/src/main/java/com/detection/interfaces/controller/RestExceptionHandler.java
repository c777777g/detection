package com.detection.interfaces.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.commonsl.util.CustomException;
import com.commonsl.util.ErrorCode;
import com.commonsl.util.JsonResult;



@Controller
public class RestExceptionHandler {



    //自定义异常
    @ExceptionHandler(CustomException.class)  
    @ResponseBody  
    public JsonResult CustomExceptionHandler(ErrorCode errorCode) {  
        return new JsonResult(errorCode);
    } 
    
   
}
