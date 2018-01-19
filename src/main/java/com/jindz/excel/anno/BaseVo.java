package com.jindz.excel.anno;

public class BaseVo {

	// 小标从0开始，默认排除表头从第二行开始读取
	private Integer startRow = 2;

	public Integer getStartRow() {
		return startRow;
	}

	public Integer getEndRow() {
		return startRow + 10;
	}
}
