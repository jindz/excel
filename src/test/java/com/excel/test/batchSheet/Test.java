package com.excel.test.batchSheet;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.jindz.excel.util.ExcelUtil;

public class Test {

	static String excel_path = "d:/qwer.xlsx";

	public static void main(String[] args) {

		try {
			Map<String, List<Object>> map = ExcelUtil.paserBatchSheet(new File(excel_path), Mapping.sheet2package);
			Iterator<Map.Entry<String, List<Object>>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, List<Object>> entry = it.next();
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
