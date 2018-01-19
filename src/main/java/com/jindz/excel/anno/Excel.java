package com.jindz.excel.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.usermodel.IndexedColors;

import com.jindz.excel.enums.DataType;

/**
 * Excel定义
 * 
 * @author jinezhi
 * @date 2015-9-22 15:34:29
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Excel {
	public static final int time = 0;
	public static final int calendar = 1;
	public static final int number = 2;
	public static final int string = 3;

	public static final String HH_MM = "HH:mm";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";

	// 无色
	public static final short DEFAULT_COLOR = 9999;

	/**
	 * 下标
	 */
	int index();

	/**
	 * 列名
	 */
	String title() default "";

	/**
	 * 内容的类型
	 */
	DataType dataType() default DataType.STRING;

	/**
	 * 如果列内容为时间，那么将按照此格式进行格式化
	 */
	String timeFormat() default HH_MM;

	/**
	 * 如果列内容为日期，那么将按照此格式进行格式化
	 */
	String CalendarFormat() default YYYY_MM_DD;

	/**
	 * 边框设置
	 */
	short border() default Border.BORDER_THIN;

	/**
	 * 背景色设置
	 */
	IndexedColors backgroundColor() default IndexedColors.WHITE;
}