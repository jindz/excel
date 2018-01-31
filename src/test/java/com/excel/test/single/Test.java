package com.excel.test.single;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.jindz.excel.exception.ValidateException;
import com.jindz.excel.util.ExcelUtil;
import com.jindz.excel.validate.BeanValidate;

public class Test {

	public static void main(String[] args) throws Exception {
		// create();
//		paser(new File("d:/1516351763910.xlsx"));
		validate();
	}

	public static void create() throws Exception {
		Long start = System.currentTimeMillis();

		List<TestVo> list = new ArrayList<TestVo>();

		for (int i = 0; i < 10000; i++) {
			TestVo test = new TestVo();

			test.setStartDate(new Date());

			test.setEndDate(new Date());

//			test.setText("qqq");

			list.add(test);
		}

		File file = ExcelUtil.create(list, TestVo.class, "d:/");

		System.out.println("完成 ,耗时:" + (System.currentTimeMillis() - start));

		System.out.println("路径：" + file.getPath());
	}

	public static void paser(File file) throws Exception {
		
		Long start = System.currentTimeMillis();
		
		Validate validate = new Validate();

		List<TestVo> lists = ExcelUtil.paser(validate, file, TestVo.class, 1);
		
		System.out.println("完成 ,耗时:" + (System.currentTimeMillis() - start));

		System.out.println("错误数量:" + validate.getErrors().size());

		System.out.println(JSON.toJSONString(lists));
	}
	
	public static void validate(){
		TestVo test = new TestVo();

		test.setStartDate(new Date());

//		test.setEndDate(new Date());

		test.setText("222");
		
		test.setIds(new String[]{"1","22"});
		try {
			BeanValidate.validateField(test);
		} catch (ValidateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
