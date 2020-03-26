package com.commonsl.util;

/**
 * 自定义异常类
 * @author Jan
 *
 */
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	private ErrorCode errorCode;	//错误码
	private String msg;				//错误信息
	

	public CustomException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CustomException(ErrorCode errorCode, String msg) {
		super();
		this.errorCode = errorCode;
		this.msg = msg;
	}


	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
