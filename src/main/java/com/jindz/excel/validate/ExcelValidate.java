package com.jindz.excel.validate;

import java.util.ArrayList;
import java.util.List;

import com.jindz.excel.exception.ValidateException;

/**
 * Excel导入数据校验抽象类
 * 
 * Validate.java</br>
 * 
 * @author jindz</br>
 * @date 2017年11月2日 上午11:06:37</br>
 *
 */
public abstract class ExcelValidate<T> {

	private List<Object> errors = new ArrayList<Object>();

	public List<Object> getErrors() {
		return errors;
	}

	/**
	 * 
	 * @author jindz
	 * @date 2017年11月8日 下午1:40:02
	 * @param t 行数据对昂
	 * @param row 行数
	 * @return boolean	-如果返回为true,该行数据会体现在最终返回的结果集中，否则相反
	 * @throws ValidateException	如果抛出了异常，整个解析Excel过程将终止
	 */
	abstract public boolean validate(T t, Integer row) throws ValidateException;

}
