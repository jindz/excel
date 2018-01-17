package com.excel.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jindz.excel.util.ExcelUtil;

public class Test {

	public static void main(String[] args) throws Exception {

		 TestVo test = new TestVo();
		
		 test.setStartDate(new Date());
		
		 test.setEndDate(new Date());
		
		 List<TestVo> list = new ArrayList<TestVo>();
		
		 list.add(test);
		
		 ExcelUtil.create(list, TestVo.class, "d:/");


	}

}
