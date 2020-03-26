package com.commonsl.util;


import com.commonsl.model.EMAIL;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;



   /*该类主要用来设置发送参数 */


public class JavaMailUtil{
	public static void sendMail(String toAddress,String massege) throws UnsupportedEncodingException, GeneralSecurityException{
		EMAIL mailInfo = new EMAIL();
		 	mailInfo.setMailServerHost(PropertyUtil.getProperty("email.MailServerHost"));
	        mailInfo.setMailServerPort(PropertyUtil.getProperty("email.MailServerPort"));
	        mailInfo.setValidate(true);
	        mailInfo.setFromNickName("验证码");
	        mailInfo.setUserName(PropertyUtil.getProperty("email.UserName")); // 实际发送者
	        mailInfo.setPassword(PropertyUtil.getProperty("email.Password"));// 您的邮箱密码
	        mailInfo.setFromAddress(PropertyUtil.getProperty("email.FromAddress")); // 设置发送人邮箱地址
	        mailInfo.setToAddress(toAddress); // 设置接受者邮箱地址  
	        mailInfo.setSubject("验证邮件");
	        mailInfo.setContent(massege);
 mailInfo.setAttachments(new ArrayList<File>(){});
  //用于添加附件
	        // 这个类主要来发送邮件   
	        JavaMailSendUtil.sendHtmlMail(mailInfo); // 发送html格式
	
	
	}
}

