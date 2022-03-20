package com.excel.test.batchSheet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.jindz.excel.util.ExcelUtil;

public class Test2 {
	
	public static void main(String[] args) {
		try {
			TestVo v = new TestVo();
			v.setGetNamegetTest("getName");
			v.setName("name");
			List<TestVo> valueList = new ArrayList<TestVo>();
			valueList.add(v);
			File f= ExcelUtil.create(valueList, TestVo.class, "d:/");
			
			List<TestVo> li = ExcelUtil.paser(f, TestVo.class, 0);
			
			System.out.println(li);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
