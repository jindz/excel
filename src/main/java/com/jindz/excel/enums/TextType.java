package com.jindz.excel.enums;

/**
 * 数据类型枚举
 * @author jindz
 *
 */
public enum TextType {
	TIME(0), CALENDAR(1), NUMBER(2), STRING(3);

	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	TextType(Integer value) {
		this.value = value;
	}
}
