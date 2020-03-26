package com.commonsl.util;


public class RedisKey {

	
	//用户模块
	/**用户信息key：user_+userCode值*/
//	public static String USER = "user_%s";
//	//设置设备
//	public static String SETDEVCIE = "set_%s";

//
//	public static String HEARTBEAT = "heartbeat_%s";
//
//	public static String REGISTERPREPARE = "registerPrepare_%s";

//	//找回密码时
	public static String FINDPASSWORD = "findP_%s";
	//上报数据报警
	public static String SENSORALARM = "sensorAlarm_%s";


	public static String HTTPSESSION = "httpSession_%s";


	public static String MONITORALARM = "monitorAlarm_%s";

	public static String LOGIN = "login_%s";
	public static String USERLOGINID = "userLoginId_%s";


	public static String AUDIORECREATIONTOUSER = "audioRecreationToUser_%s";

	public static String AUDIORECREATIONTODEVICE = "audioRecreationToDevice_%s";

	public static String AUDIOBROADCASTTOUSER = "audioBroadcastToUser_%s";
	//other model...
	
}
