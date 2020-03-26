package com.commonsl.util;



public class ErrorCode {
	private int code;
	private String msg;

	public ErrorCode() {

	}

	public ErrorCode(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	//系统编码       1000xx
	/**成功 */
	public static final ErrorCode SYS_SUSSES = new ErrorCode(100001,"成功");
	/**失败 */
	public static final ErrorCode SYS_ERR = new ErrorCode(100002,"失败");
	/**参数不完整 */
	public static final ErrorCode SYS_PARAM_VALUE_ERROR = new ErrorCode(100003,"参数错误");
//	/**请求方式错误 */
//	public static final ErrorCode SYS_REQUEST_METHOD_ERROR  = new ErrorCode(100004, "请求方式错误");
//	/**连接超时 */
//	public static final ErrorCode SYS_CONNECTION_TIMED_OUT= new ErrorCode(100005, "连接超时");
//	/**请求频繁 */
//	public static final ErrorCode SYS_REQUEST_OFTEN= new ErrorCode(100006, "请求频繁");
	
	
	//用户模块编码 2000xx
	/**手机号码已注册 */
	public static final ErrorCode CUSTOM_PHONE_EXITS  = new ErrorCode(200001, "手机号码已注册");
	/**验证码不正确 */
	//public static final ErrorCode CUSTOM_YANGZHENGMA_ERROR = new ErrorCode(200003, "验证码不正确");
	/**验证码已过期 */
	//public static final ErrorCode CUSTOM_YANGZHENGMA_EXPIRED  = new ErrorCode(200004, "验证码已过期");
	/**账号或密码错误 */
	public static final ErrorCode CUSTOM_USER_PWD_ERROR  = new ErrorCode(200005, "账号或密码错误");
	/**用户已存在 */
	public static final ErrorCode CUSTOM_EXIST = new ErrorCode(200006, "用户已存在");
	/**用户不存在 */
	//public static final ErrorCode CUSTOM_NOT_EXIST = new ErrorCode(200007, "用户不存在");
	/**无效的token */
	public static final ErrorCode CUSTOM_TOKEN_INVALID  = new ErrorCode(200008, "无效的token");
	/**验证码发送失败 */
	public static final ErrorCode CUSTOM_YANGZHENGMA_SEND_ERROR  = new ErrorCode(200009, "验证码发送失败");
	/**非法的用户 */
	//public static final ErrorCode CUSTOM_TOKEN_USER_INVALID  = new ErrorCode(200010, "非法的用户");
	/**签名错误 */
	public static final ErrorCode CUSTOM_SIGN_ERROR  = new ErrorCode(200011, "签名错误");
	/**验证码已发送 */
	//public static final ErrorCode CUSTOM_YANGZHENGMA_SEND_ALREADY  = new ErrorCode(200012, "验证码已发送");
	/**账号已停用 */
	//public static final ErrorCode CUSTOM_STATUS_STOP  = new ErrorCode(200013, "账号已停用");
	/**已绑定第三方平台账号 */
	//public static final ErrorCode CUSTOM_THIRDACCOUNT_BIND  = new ErrorCode(200014, "已绑定第三方平台账号");

	/**未绑定第三方平台账号*/
	//public static final ErrorCode CUSTOM_THIRDACCOUNT_NOT_BIND  = new ErrorCode(200018, "未绑定第三方平台账号");
	/**支付密码错误*/
	//public static final ErrorCode CUSTOM_PAYPWD_ERROR  = new ErrorCode(200019, "支付密码错误");
	/**手机号码未注册*/
	//public static final ErrorCode CUSTOM_PHONE_NOT_REGISTER  = new ErrorCode(200020, "手机号码未注册");
	/**手机号码不能为空*/
	public static final ErrorCode CUSTOM_PHONE_NOT_NULL  = new ErrorCode(200021, "手机号不能为空");
	/**原密码错误*/
	public static final ErrorCode CUSTOM_OLD_PWD_ERROR  = new ErrorCode(200022, "原密码错误");
	
	//后台模块编码 3000xx
	/**角色名已存在，请勿重复添加 */
	public static final ErrorCode ROLENAME_EXITS  = new ErrorCode(300001, "角色名已存在，请勿重复添加");
//	/**请至少选择一个角色 */
//	public static final ErrorCode ROLE_NOT_NULL  = new ErrorCode(300002, "请至少选择一个角色");
//	/**该用户已经配有角色，如需修改，请使用修改功能 */
//	public static final ErrorCode USER_AGENT_EXITS  = new ErrorCode(300003, "该用户已经配有角色，如需修改，请使用修改功能");

	//商品模块
	/**没有该商品*/
	public static final ErrorCode SKU_NOT_EXIST = new ErrorCode(600001,"没有该商品");
	public static final ErrorCode SKU_MORE_THAN_ONE = new ErrorCode(600002,"查到的商品数目大于一");
	//收货人模块
	public static final ErrorCode ARRRESS_MORE_THAN_20 = new ErrorCode(700001,"收货地址不能大于20条");
}
