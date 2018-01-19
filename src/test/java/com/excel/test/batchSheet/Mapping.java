package com.excel.test.batchSheet;

import java.util.HashMap;
import java.util.Map;

public class Mapping {

	// sheet与java包路径名的映射
	static Map<String, String> sheet2package = new HashMap<String, String>();

	static {
		sheet2package.put("表格1", "com.excel.test.batchSheet.vo");
		sheet2package.put("表格2", "com.excel.test.batchSheet.vo");
	}
}
