package com.excel.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jindz.excel.util.ExcelUtil;

public class Test {

	public static void main(String[] args) throws Exception {

		TestVo test = new TestVo();

		test.setStartDate(new Date());

		test.setEndDate(new Date());
		
		test.setText("你好");

		List<TestVo> list = new ArrayList<TestVo>();

		list.add(test);

		File file = ExcelUtil.create(list, TestVo.class, "d:/");
		
		System.out.println(file.getPath());

		List<TestVo> lists = ExcelUtil.paser(new Validate(),file, TestVo.class, 1);
		
		System.out.println(JSON.toJSONString(lists));
	}

}
