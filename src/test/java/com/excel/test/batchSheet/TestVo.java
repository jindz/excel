package com.excel.test.batchSheet;

import com.jindz.excel.anno.Excel;

public class TestVo {

	@Excel(index = 0, title = "name")
	private String name;
	@Excel(index = 1, title = "getName")
	private String getNamegetTest;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGetNamegetTest() {
		return getNamegetTest;
	}

	public void setGetNamegetTest(String getNamegetTest) {
		this.getNamegetTest = getNamegetTest;
	}


}
