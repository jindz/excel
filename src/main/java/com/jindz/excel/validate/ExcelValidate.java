package com.jindz.excel.validate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private List<Object> error = new ArrayList<Object>();

	public List<Object> getError() {
		return error;
	}

	/**
	 * 
	 * @author jindz
	 * @date 2017年11月8日 下午1:40:02
	 * @param t
	 *            行数据
	 * @param row
	 *            行
	 * @return
	 * @throws ValidateException
	 */
	abstract public boolean validate(T t, Integer row) throws ValidateException;

}
