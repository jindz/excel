package com.jindz.excel.exception;

/**
 * @author jindz
 * @version 1.0.0
 * @description
 * @date 2017/10/30
 */
public class ValidateException extends Exception {
	private String code;
	private String message;
	private Class clazz;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public ValidateException() {
	}

	/**
	 * 构造器
	 * 
	 * @param code	错误编码
	 * @param message 消息
	 * @param clazz  可以告诉捕获错误的开发者具体是哪一个业务模块出现问题的Class
	 */
	public ValidateException(String code, String message, Class clazz) {
		super();
		this.code = code;
		this.message = message;
		this.clazz = clazz;
	}
}
