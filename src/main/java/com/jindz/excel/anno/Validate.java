package com.jindz.excel.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jindz.excel.enums.ExceptionEnum;


/**
 * 对象空值校验,用来检查必填字段是否为空
 * 
 * Validate.java</br>
 * 
 * @author jindz</br>
 * @date 2017年10月25日 上午9:53:11</br>
 * 
 * </br>
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Validate {
	
	/**
	 * 校验失败，跑出的异常枚举
	 */
	ExceptionEnum exception();
	
	/**
	 * 最小长度
	 */
	String minLen() default "";
	/**
	 * 最大长度
	 */
	String maxLen() default "";
	/**
	 * 是否必传
	 */
	boolean isRequired() default true;
	/**
	 * 前置条件
	 */
	String iff() default "";
}
