package com.jindz.excel.enums;

/**
 * Created by hx on 2017/10/26. 异常映射码表
 */
public enum ExceptionMappingEnum {

	SYSTEM_ERROR("-1", " 系统错误"), 
	EXCEL_TITLE_ERROR("00000060", "Excel中的表头与代码注解中的表头不一致");

	private String errorCode;
	private String errorMsg;

	ExceptionMappingEnum(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public String getErrorCode() {
		return errorCode;
	}

}
