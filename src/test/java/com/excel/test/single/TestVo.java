package com.excel.test.single;

import java.util.Date;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.jindz.excel.anno.Excel;
import com.jindz.excel.anno.Validate;
import com.jindz.excel.enums.DataType;

public class TestVo {

	@Validate(errorCode = "001", errorMsg = "开始时间不能为空")
	@Excel(index = 0, title = "开始时间", backgroundColor = IndexedColors.RED, dataType = DataType.CALENDAR, calendarFormat = "yyyy-MM-dd")
	private Date startDate;

	@Validate(errorCode = "002", errorMsg = "结束时间不能为空")
	@Excel(index = 1, title = "结束时间", dataType = DataType.CALENDAR, calendarFormat = "yyyy-MM-dd")
	private Date endDate;

	@Validate(errorCode = "003", errorMsg = "内容不能为空")
	@Excel(index = 2, title = "内容")
	private String text;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
