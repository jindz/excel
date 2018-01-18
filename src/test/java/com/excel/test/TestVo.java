package com.excel.test;

import java.util.Date;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.jindz.excel.anno.Excel;
import com.jindz.excel.enums.TextType;

public class TestVo {

	@Excel(index = 0, title = "开始时间", backgroundColor = IndexedColors.RED, textType = TextType.CALENDAR, CalendarFormat = "yyyy-MM-dd")
	private Date startDate;

	@Excel(index = 1, title = "结束时间", textType = TextType.CALENDAR, CalendarFormat = "yyyy-MM-dd")
	private Date endDate;

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

}
