package com.excel.test;

import java.util.Date;

import com.jindz.excel.anno.Excel;

public class TestVo {
	
	@Excel(index=0,textType = Excel.calendar,CalendarFormat = "yyyy-MM-dd")
	private Date startDate;
	
	@Excel(index=1,textType = Excel.calendar,CalendarFormat = "yyyy-MM-dd")
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
