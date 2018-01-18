package com.excel.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.jindz.excel.util.ExcelUtil;

public class Test {

	public static void main(String[] args) throws Exception {

		 TestVo test = new TestVo();
		
		 test.setStartDate(new Date());
		
		 test.setEndDate(new Date());
		
		 List<TestVo> list = new ArrayList<TestVo>();
		
		 list.add(test);
		
		 File file = ExcelUtil.create(list, TestVo.class, "d:/");

		 System.out.println(file.getPath());
		

	}

}
