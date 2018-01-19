package com.jindz.excel.validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jindz.excel.anno.Validate;


/**
 * 公共抽象校验类,检验业务bean中的字段
 * 
 * AbstractValidate.java</br>
 * 
 * @author jindz</br>
 * @date 2017年10月25日 上午10:01:36</br>
 * 
 *       益丰公司版权所有 </br>
 */
public class BaseValidate {

	// 运算符定义
	static final String dy = "==";
	static final String ndy = "!=";
	static final String day = ">";
	static final String xy = "<";
	static final String like = "%";// 包含，用于数组

	private static final String key = "config";

	private static ThreadLocal<ConcurrentHashMap<String, Object>> config = new ThreadLocal<ConcurrentHashMap<String, Object>>();

	/**
	 * 校验对象所有必传字段是否存在null，存在nul直接抛出ValidateException TODO
	 * 
	 * @author jindz
	 * @date 2017年10月25日 上午11:19:36
	 * @param target
	 * @throws Exception
	 */
	public static <T> void validateField(T... target) throws ValidateException {
		if (target == null) {
			throw new ValidateException("1001", "参数不能为空！", BaseValidate.class);
		} else {
			try {
				for (int i = 0; i < target.length; i++) {
					validate(target[i], null);
				}
			} catch (ValidateException e) {
				throw e;
			} finally {
				removeConfig();
			}
		}
	}

	/**
	 * 校验对象所有必传字段是否存在null，异常信息封装在errors中 TODO
	 * 
	 * @author jindz
	 * @date 2017年10月25日 上午11:14:50
	 * @param target
	 * @param errors
	 */
	public static <T> void validateField(Map<String, String> errors, T... target) throws ValidateException {
		if (target == null) {
			throw new ValidateException("1001", "参数不能为空！", BaseValidate.class);
		} else {
			try {
				for (int i = 0; i < target.length; i++) {
					validate(target[i], errors);
				}
			} catch (ValidateException e) {
				throw e;
			} finally {
				removeConfig();
			}
		}
	}

	/**
	 * 校验
	 * 
	 * @author jindz
	 * @date 2017年10月27日 上午9:29:44
	 * @param target
	 * @param errors
	 * @throws ValidateException
	 */
	private static void validate(Object target, Map<String, String> errors) throws ValidateException {
		List<Method> method = getmethodList(target);
		for (int i = 0; i < method.size(); i++) {
			try {
				String fieldName = method.get(i).getName().split("get")[1];
				String first = fieldName.substring(0, 1).toLowerCase();
				String last = fieldName.substring(1, fieldName.length());
				Field fields = target.getClass().getDeclaredField(first + last);
				if (fields.isAnnotationPresent(Validate.class)) {
					Map<String, Object> comment = parseComment(fields);
					Object obj = method.get(i).invoke(target);
					// 1、校验是否可以为空
					Boolean isRequired = Boolean.valueOf(comment.get("isRequired").toString());
					if (isRequired) {
						if (obj == null || org.apache.commons.lang3.StringUtils.isEmpty(toString(obj))) {
							// 不能为空的前置条件
							if (iff(method, fields, target)) {
								errorHander(errors, comment);
							}
						}
					}
					// 2、校验长度
					Integer length = toString(obj).length();
					Integer minLen = toInteger(comment.get("minLen"));
					if (minLen > 0) {
						if (length < minLen) {
							errorHander(errors, comment);
						}
					}
					Integer maxLen = toInteger(comment.get("maxLen"));
					if (maxLen > 0) {
						if (length > maxLen) {
							errorHander(errors, comment);
						}
					}

				}
			} catch (ValidateException e) {
				throw e;
			} catch (Exception e) {
				throw new ValidateException("1001", "系统异常!" + e.getMessage(), BaseValidate.class);
			}
		}

	}

	private static void errorHander(Map<String, String> errors, Map<String, Object> comment) throws ValidateException {
		String errorCode = toString(comment.get("errorCode"));
		String errorMsg = toString(comment.get("errorMsg"));
		if (errors != null) {
			errors.put(errorCode, errorMsg);
		} else {
			throw new ValidateException(errorCode, errorMsg, BaseValidate.class);
		}
	}

	private static boolean iff(List<Method> methods, Field fields, Object target) throws Exception {
		String iff = toString(parseComment(fields).get("iff"));
		if (org.apache.commons.lang3.StringUtils.isEmpty(iff)) {
			return true;
		}
		try {
			if (iff.indexOf(dy) != -1) {
				String fid = iff.split(dy)[0];
				String value = iff.split(dy)[1];
				// 获取实际值
				String methodName = "get" + fid.substring(0, 1).toUpperCase() + fid.substring(1, fid.length());
				Method method = getmethodByName(methods, methodName);
				if (method != null) {
					String factValue = toString(method.invoke(target));
					if (factValue.equals(value)) {
						return true;
					}
				}
			} else if (iff.indexOf(ndy) != -1) {
				String fid = iff.split(ndy)[0];
				String value = iff.split(ndy)[1];
				// 获取实际值
				String methodName = "get" + fid.substring(0, 1).toUpperCase() + fid.substring(1, fid.length());
				Method method = getmethodByName(methods, methodName);
				if (method != null) {
					String factValue = toString(method.invoke(target));
					if (!factValue.equals(value)) {
						return true;
					}
				}
			} else if (iff.indexOf(day) != -1) {
				String fid = iff.split(day)[0];
				Integer value = toInteger(iff.split(day)[1]);
				// 获取实际值
				String methodName = "get" + fid.substring(0, 1).toUpperCase() + fid.substring(1, fid.length());
				Method method = getmethodByName(methods, methodName);
				if (method != null) {
					Integer factValue = toInteger(method.invoke(target));
					if (factValue > value) {
						return true;
					}
				}
			} else if (iff.indexOf(xy) != -1) {
				String fid = iff.split(xy)[0];
				Integer value = toInteger(iff.split(xy)[1]);
				// 获取实际值
				String methodName = "get" + fid.substring(0, 1).toUpperCase() + fid.substring(1, fid.length());
				Method method = getmethodByName(methods, methodName);
				if (method != null) {
					Integer factValue = toInteger(method.invoke(target));
					if (factValue < value) {
						return true;
					}
				}
			} else if (iff.indexOf(like) != -1) {
				String fid = iff.split(like)[0];
				String value = toString(iff.split(like)[1]);
				String methodName = "get" + fid.substring(0, 1).toUpperCase() + fid.substring(1, fid.length());
				Method method = getmethodByName(methods, methodName);
				if (method != null) {
					// 获取实际值
					String[] factValue = (String[]) method.invoke(target);
					if (factValue != null) {
						for (int i = 0; i < factValue.length; i++) {
							if (factValue[i].equals(value)) {
								return true;
							}
						}
					}
				}
			} else {
				throw new Exception("错误的校验条件定义:" + iff);
			}
		} catch (Exception e) {
			throw new Exception("错误的校验条件定义:" + iff);
		}

		return false;
	}

	private static String toString(Object obj) {
		if (obj != null) {
			return String.valueOf(obj);
		}
		return "";
	}

	private static Integer toInteger(Object obj) {
		try {
			if (obj != null && !org.apache.commons.lang3.StringUtils.isEmpty("" + obj)) {
				return Integer.valueOf(toString(obj));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	private static Method getmethodByName(List<Method> list, String methodName) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equals(methodName)) {
				return list.get(i);
			}
		}
		return null;
	}

	private static List<Method> getmethodList(Object target) {

		Object config = getConfig();
		List<Method> methodList = new ArrayList<Method>();
		try {
			if (config != null) {
				List<Method> list = new ArrayList<Method>();
				String[] field = (String[]) config;
				for (String s : field) {
					list.add(target.getClass().getDeclaredMethod(
							"get" + s.substring(0, 1).toUpperCase() + s.substring(1, s.length())));
				}
				return list;
			}
			Method[] method = target.getClass().getDeclaredMethods();
			for (int i = 0; i < method.length; i++) {
				if (method[i].getName().indexOf("get") != -1) {
					methodList.add(method[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return methodList;
	}

	private static Map<String, Object> parseComment(Field field) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Annotation p = field.getAnnotation(Validate.class);
		Method[] methods = p.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			try {
				map.put(methods[i].getName(), methods[i].invoke(p));
			} catch (Exception e) {
				// Ignore
			}
		}
		return map;
	}

	private static Object getConfig() {
		return config.get() == null ? null : config.get().get(key);
	}

	private static void removeConfig() {
		try {
			if (config.get() != null) {
				config.get().remove(key);
			}
		} catch (Exception e) {
		}
	}

	public static BaseValidate config(String... field) {
		if (config.get() == null) {
			ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<String, Object>();
			map.put(key, field);
			config.set(map);
		} else {
			config.get().put(key, field);
		}
		return new BaseValidate();
	}

}
