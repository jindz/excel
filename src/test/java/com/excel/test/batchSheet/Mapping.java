package com.excel.test.batchSheet;

import java.util.HashMap;
import java.util.Map;


/**
 * 批量解析excel(多个sheet&&每个sheet多个表单)
 * @author Administrator
 *
 */
public class Mapping {

	// sheet与java包路径名的映射
	static Map<String, String> sheet2package = new HashMap<String, String>();

	static {
		sheet2package.put("表格1", "com.excel.test.batchSheet.vo");
		sheet2package.put("表格2", "com.excel.test.batchSheet.vo");
	}
}
