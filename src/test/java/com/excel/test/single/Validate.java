package com.excel.test.single;

import com.alibaba.fastjson.JSON;
import com.jindz.excel.validate.BeanValidate;
import com.jindz.excel.validate.ExcelValidate;
import com.jindz.excel.validate.ValidateException;

public class Validate extends ExcelValidate<TestVo> {

	@Override
	public boolean validate(TestVo t, Integer row) throws ValidateException {
		// TODO Auto-generated method stub
		System.out.println("begin validate row:" + row + ", data:"
				+ JSON.toJSONString(t));

		try {
			BeanValidate.validateField(t);
		} catch (Exception e) {
			this.getError().add(e.getMessage());
			return false;
		}

		return true;
	}

}
