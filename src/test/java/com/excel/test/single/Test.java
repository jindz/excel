package com.excel.test.single;

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

		test.setText("");

		List<TestVo> list = new ArrayList<TestVo>();

		list.add(test);

		File file = ExcelUtil.create(list, TestVo.class, "d:/");

		System.out.println(file.getPath());

		Validate validate = new Validate();

		List<TestVo> lists = ExcelUtil.paser(validate, file, TestVo.class, 1);
		
		System.out.println(validate.getError());

		System.out.println(JSON.toJSONString(lists));
	}

}
