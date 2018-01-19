package com.jindz.excel.anno;

public class BaseVo {

	// 下标从0开始，默认排除表头从第二行开始读取
	private Integer startRow = 1;

	public Integer getStartRow() {
		return startRow;
	}

	public Integer getEndRow() {
		return startRow + 10;
	}
}
