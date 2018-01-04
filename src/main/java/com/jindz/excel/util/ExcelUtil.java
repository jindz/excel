package com.jindz.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.alibaba.fastjson.JSON;
import com.jindz.excel.anno.BaseVo;
import com.jindz.excel.anno.Excel;
import com.jindz.excel.enums.ExceptionMappingEnum;
import com.jindz.excel.validate.ExcelValidate;
import com.jindz.excel.validate.ValidateException;

/**
 * Excel导入导出
 * 
 * @author jindezhi
 * 
 * @createDate 2015-9-22 15:01:29
 * 
 */
public abstract class ExcelUtil {
	

    public static final String FORMAT_TIME = "HH:mm";

    public static final String FORMAT_CALENDAR = "yyyy-MM-dd";

    /**
     * @desc 解析 excel
     * @author jindz
     * @param file 文件
     * @param startLine 开始解析行号
     * 
     * @return List<?>
     * 
     * 
     * @throws Exception
     * 
     * @createDate 2015-9-22 15:01:29
     * 
     */
    public static <T> List<T> paser(ExcelValidate validate,File file, Class<T> classz, int startLine) throws Exception {

        // 2003
        HSSFWorkbook hssfWorkbook = null;
        // 2007
        XSSFWorkbook xssfWorkbook = null;
        List<T> dataList = new ArrayList();
        try {
            hssfWorkbook = new HSSFWorkbook(new FileInputStream(file));
        } catch (Exception e) {
            xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
        }
        if (hssfWorkbook != null) {
            parseBy2003(validate,hssfWorkbook, classz, dataList, file, startLine);
        } else {
            parseBy2007(validate,xssfWorkbook, classz, dataList, file, startLine);
        }
        return dataList;
    }

    public static Object getBook(File file) throws Exception {
        try {
            // 2003
            return new HSSFWorkbook(new FileInputStream(file));
        } catch (Exception e) {
            // 2007
            return new XSSFWorkbook(new FileInputStream(file));
        }
    }

    public static int getNumberOfSheets(Object book) {
        if (book instanceof HSSFWorkbook) {
            // 2003
            return ((HSSFWorkbook) book).getNumberOfSheets();
        } else if (book instanceof XSSFWorkbook) {
            // 2007
            return ((XSSFWorkbook) book).getNumberOfSheets();
        } else {
            return 0;
        }
    }

    private static Object getSheetAt(Object book, int numSheet) {
        if (book instanceof HSSFWorkbook) {
            // 2003
            return ((HSSFWorkbook) book).getSheetAt(numSheet);
        } else if (book instanceof XSSFWorkbook) {
            // 2007
            return ((XSSFWorkbook) book).getSheetAt(numSheet);
        } else {
            return null;
        }
    }

    private static String getSheetName(Object sheet) {
        if (sheet instanceof HSSFSheet) {
            // 2003
            return ((HSSFSheet) sheet).getSheetName();
        } else if (sheet instanceof XSSFSheet) {
            // 2007
            return ((XSSFSheet) sheet).getSheetName();
        } else {
            return "";
        }
    }

    private static Integer getLastRowNum(Object sheet) {
        if (sheet instanceof HSSFSheet) {
            // 2003
            return ((HSSFSheet) sheet).getLastRowNum();
        } else if (sheet instanceof XSSFSheet) {
            // 2007
            return ((XSSFSheet) sheet).getLastRowNum();
        } else {
            return null;
        }

    }

    private static Object getRow(Object sheet, int rowNum) {
        if (sheet instanceof HSSFSheet) {
            // 2003
            return ((HSSFSheet) sheet).getRow(rowNum);
        } else if (sheet instanceof XSSFSheet) {
            // 2007
            return ((XSSFSheet) sheet).getRow(rowNum);
        } else {
            return null;
        }

    }

    private static Object getCell(Object row, int cell) {
        if (row instanceof XSSFRow) {
            // 2007
            return ((XSSFRow) row).getCell(cell);
        } else if (row instanceof HSSFRow) {
            // 2003
            return ((HSSFRow) row).getCell(cell);
        }
        return null;

    }

    /**
     * 
     * 解析excel，多个Sheet情况下<br/>
     * 
     * @author jindz
     * @date: 2017/01/12 上午 11:49:14
     * @version 1.0
     * 
     * @param file
     * @param startLine
     * @param packageName 包名,Sheet必须以类名命
     * @return
     * @throws Exception
     */
    public static Map<String, List<Object>> paserBatchSheet(ExcelValidate validate,File file, int startLine, String packageName) throws Exception {

        Object book = getBook(file);

        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        if (book != null) {
            int numberOfSheets = getNumberOfSheets(book);
            for (int numSheet = 0; numSheet < numberOfSheets; numSheet++) {
                try {
                    Object sheet = getSheetAt(book, numSheet);
                    if (sheet == null) {
                        continue;
                    }
                    String classzName = getSheetName(sheet);
                    Class<?> classz = Class.forName(packageName + classzName);
                    List<Object> dataList = new ArrayList<Object>();
                    parseSheet(validate,sheet, classz, dataList, startLine);
                    map.put(classzName, dataList);
                } catch (Exception e) {

                    String msg = "Excel parse fail. error info:" + e.getMessage();

                    if (e instanceof ClassNotFoundException) {
                        msg = "Excel parse fail. error info:" + "ClassNotFoundException:" + e.getMessage();
                    }
                    throw new RuntimeException(msg);
                }
            }
        }
        return map;
    }

    /**
     * 
     * 解析excel，多个Sheet情况<br/>
     * 
     * @author jindz
     * @date: 2017/01/12 上午 11:49:14
     * @version 1.0
     * 
     * @param file
     * @param mapping sheet与class映射对象
     * @return
     * @throws Exception
     */
    public static Map<String, List<Object>> paserBatchSheet(ExcelValidate validate,File file,Map<String, String> mapping) throws Exception {
        Object book = getBook(file);
        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        if (book != null) {
            int numberOfSheets = getNumberOfSheets(book);
            for (int numSheet = 0; numSheet < numberOfSheets; numSheet++) {
                try {
                    Object sheet = getSheetAt(book, numSheet);
                    if (sheet == null) {
                        continue;
                    }
                    String sheetName = getSheetName(sheet);
                    String packageName = mapping.get(sheetName);
                    Set<Class<?>> classz = ClassUtil.getClassSet(packageName);
                    Iterator<Class<?>> it = classz.iterator();
                    while (it.hasNext()) {

                        Class<?> clz = it.next();
                        List<Object> dataList = new ArrayList<Object>();

                        BaseVo instans = (BaseVo) clz.newInstance();
                        parseSheet2(validate,sheet, clz, dataList, instans.getStartLine(), instans.getEndLine());
                        if (map.get(sheetName) == null) {
                            map.put(sheetName, dataList);
                        } else {
                            map.get(sheetName).addAll(dataList);
                        }
                    }
                } catch (Exception e) {
                    String msg = "Excel parse fail. error info:" + e.getMessage();
                    if (e instanceof ClassNotFoundException) {
                        msg = "Excel parse fail. error info:" + "ClassNotFoundException:" + e.getMessage();
                    }
                    throw new RuntimeException(msg);
                }
            }
        }
        return map;
    }

    /**
     * @desc Create a version 2007 Excel
     * @author jindz
     * @param valueList 数据结果集
     * @param fileName 文件
     * @param saveDirectory 保存目录
     * @param classz 类名
     * @return File
     * 
     * @throws Exception
     * @createDate 2013-12-04 18:07:05
     * 
     */
    public static File create(List<?> valueList, Class<?> classz, String saveDirectory) throws Exception {
        if ("Map".equals(getType(classz).getSimpleName())) {
            throw new Exception("Class Can't be Map.");
        }
        XSSFWorkbook xssBook = new XSSFWorkbook();
        XSSFSheet sheet = xssBook.createSheet(getType(classz).getSimpleName());
        List<Method> methodList = getmethodList("get", getType(classz).getDeclaredMethods());

        for (int i = 0; i < valueList.size(); i++) {
            Object obj = JSON.parseObject(JSON.toJSONString(valueList.get(i)), getType(classz));
            for (int j = 0; j < methodList.size(); j++) {
                Map<String, Object> comment = null;
                String value = toString(methodList.get(j).invoke(obj));

                // parse comment
                Field filed = getFieldByMethod(methodList.get(j), obj);
                if (filed.isAnnotationPresent(Excel.class)) {
                    comment = parseComment(filed);
                    String title = toString(comment.get("title"));
                    int titleIndex = toInt(comment.get("index"));
                    Integer valueType = toInt(comment.get("textType"));
                    String timeFormat = toString(comment.get("timeFormat"));
                    String calendarFormat = toString(comment.get("CalendarFormat"));

                    // set text
                    setText(xssBook, sheet, i, title, titleIndex, value, valueType, timeFormat, calendarFormat);
                    // set Style
                    if (comment != null) {
                        setStype(comment, xssBook, sheet, i, titleIndex);
                    }
                }

            }
        }
        File file = FileUtil.createFileAsFile(saveDirectory, System.currentTimeMillis() + ".xlsx");
        OutputStream stream = new FileOutputStream(file);
        xssBook.write(stream);
        stream.close();
        return file;
    }
    
    private static Field getFieldByMethod(Method method,Object target) throws NoSuchFieldException, SecurityException{
    	String fieldName = method.getName().split("get")[1];
		String first = fieldName.substring(0, 1).toLowerCase();
		String last = fieldName.substring(1, fieldName.length());
		return target.getClass().getDeclaredField(first + last);
    }
    
    /**
     * 获取第一行的标题名称
     * @author jindz
     * @date 2017年12月6日 下午7:12:38
     * @param cell 列下标
     * @param FristRow 行对象
     * @return
     */
    private static String getFristRowTitleName(int cell,XSSFRow fristRow){
    	XSSFCell xssfCell = fristRow.getCell(cell);
    	return getValue(xssfCell);
    }
    
    /**
     * 获取第一行的标题名称
     * @author jindz
     * @date 2017年12月6日 下午7:12:38
     * @param cell 列下标
     * @param HSSFRow 行对象
     * @return
     */
    private static String getFristRowTitleName(int cell,HSSFRow fristRow){
    	HSSFCell xssfCell = fristRow.getCell(cell);
    	return getValue(xssfCell);
    }
    
    /**
     * 校验excel与注解中的title是否一致
     * @author jindz
     * @date 2017年12月6日 下午7:27:48
     * @param excelTitleName
     * @param annoTitleName
     */
    private static void validateTitleName(String excelTitleName,String annoTitleName) throws ValidateException{

    	if(!StringUtils.isEmpty(annoTitleName)){
    		if(!annoTitleName.trim().equals(excelTitleName==null?"":excelTitleName.trim())){
        		throw new ValidateException(ExceptionMappingEnum.EXCEL_TITLE_ERROR.getErrorCode(),
        				ExceptionMappingEnum.EXCEL_TITLE_ERROR.getErrorMsg(), ExcelUtil.class);
        	}
    	}
    
    }

    /**
     * @project parse excel version 2007
     * @author jindz
     * @throws Exception
     * 
     * @createDate 2015-9-22 13:28:22
     * 
     */
    private static <T> void parseBy2007(ExcelValidate validate,XSSFWorkbook xssfWorkbook, Class<?> classz, List<T> dataList, File file, int startLine)
            throws Exception {
        for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null || rowNum < startLine || isNullRow(xssfRow)) {
                    continue;
                }
                Object object = getType(classz).newInstance();
                List<Method> method = getmethodList("get", getType(classz).getMethods());
                for (int i = 0; i < method.size(); i++) {
                    try {
                    	Field field = getFieldByMethod(method.get(i), object);
                        if (field.isAnnotationPresent(Excel.class)) {
                            int cell = toInt(parseComment(field).get("index"));
                            validateTitleName(getFristRowTitleName(cell, xssfSheet.getRow(startLine-1)), 
                            		toString(parseComment(field).get("title")));
                            String value = getValue(xssfRow.getCell(cell));
                            Method setMethod = getType(classz).getDeclaredMethod("set" + method.get(i).getName().split("get")[1],
                                    method.get(i).getReturnType());
                            setMethod.invoke(object, generateParameter(value, method.get(i)));
                        }
                    } catch(ValidateException e){
                    	throw e;
                    } catch (Exception e) {
                        // Ignore
                    }
                }
                boolean falg = true;
                if(validate!=null){
                	falg = validate.validate(object,rowNum);
                }
                if(falg){
            		dataList.add((T) object);
            	}
            }
        }
    }

    /**
     * @project parse excel version 2007
     * @author jindz
     * @throws Exception
     * 
     * @createDate 2015-9-22 13:28:22
     * 
     */
    private static <T> void parseSheet(ExcelValidate validate,Object sheet, Class<?> classz, List<T> dataList, int startLine) throws Exception {
        if (sheet == null) {
            return;
        }
        Integer lastRowNum = getLastRowNum(sheet);
        for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
            Object row = getRow(sheet, rowNum);
            if (row == null || rowNum < startLine || isNullRow(row)) {
                continue;
            }
            Object object = getType(classz).newInstance();
            List<Method> method = getmethodList("get", getType(classz).getMethods());
            for (int i = 0; i < method.size(); i++) {
                try {
                	Field field = getFieldByMethod(method.get(i), object);
                    if (field.isAnnotationPresent(Excel.class)) {
                        int cell = toInt(parseComment(field).get("index"));
                        String value = getValue(getCell(row, cell));
                        Method setMethod = getType(classz).getDeclaredMethod("set" + method.get(i).getName().split("get")[1],
                                method.get(i).getReturnType());
                        setMethod.invoke(object, generateParameter(value, method.get(i)));
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }
            boolean falg = true;
            if(validate!=null){
            	falg = validate.validate(object,rowNum);
            }
            if(falg){
        		dataList.add((T) object);
        	}
        }

    }

    /**
     * @project parse excel version 2007
     * @author jindz
     * @throws Exception
     * 
     * @createDate 2015-9-22 13:28:22
     * 
     */
    private static <T> void parseSheet2(ExcelValidate validate,Object sheet, Class<?> classz, List<T> dataList, int startLine, int endLine)
            throws Exception {
        if (sheet == null) {
            return;
        }
        Integer lastRowNum = endLine;
        for (int rowNum = 0; rowNum <= lastRowNum; rowNum++) {
            Object row = getRow(sheet, rowNum);
            if (row == null || rowNum < startLine || isNullRow(row)) {
                continue;
            }
            Object object = getType(classz).newInstance();
            List<Method> method = getmethodList("get", getType(classz).getMethods());
            for (int i = 0; i < method.size(); i++) {
                try {
                	Field field = getFieldByMethod(method.get(i), object);
                    if (field.isAnnotationPresent(Excel.class)) {
                        int cell = toInt(parseComment(field).get("index"));
                        String value = getValue(getCell(row, cell));
                        Method setMethod = getType(classz).getDeclaredMethod("set" + method.get(i).getName().split("get")[1],
                                method.get(i).getReturnType());
                        setMethod.invoke(object, generateParameter(value, method.get(i)));
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }
            boolean falg = true;
            if(validate!=null){
            	falg = validate.validate(object,rowNum);
            }
            if(falg){
        		dataList.add((T) object);
        	}
        }

    }

    /**
     * @project parse excel version 2003
     * @author jindz
     * @param <T>
     * @param
     * @throws Exception
     * 
     * @createDate 2015-9-22 13:28:22
     * 
     */
    private static <T> void parseBy2003(ExcelValidate validate,HSSFWorkbook hssfWorkbook, Class<?> classz, List<T> dataList, File file, int startLine)
            throws Exception {
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            HSSFSheet xssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (xssfSheet == null) {
                continue;
            }
            for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow xssfRow = xssfSheet.getRow(rowNum);
                if (xssfRow == null || rowNum < startLine || isNullRow(xssfRow)) {
                    continue;
                }
                Object object = getType(classz).newInstance();
                List<Method> method = getmethodList("get", getType(classz).getMethods());
                for (int i = 0; i < method.size(); i++) {
                    try {
                    	Field field = getFieldByMethod(method.get(i), object);
                        if (field.isAnnotationPresent(Excel.class)) {
                            Map map = parseComment(field);
                            int cell = toInt(map.get("index"));
                            validateTitleName(getFristRowTitleName(cell, xssfSheet.getRow(startLine-1)), 
                            		toString(parseComment(field).get("title")));
                            String value = getValue(xssfRow.getCell(cell));
                            Method setMethod = getType(classz).getDeclaredMethod("set" + method.get(i).getName().split("get")[1],
                                    method.get(i).getReturnType());
                            setMethod.invoke(object, generateParameter(value, method.get(i)));
                        }
                    } catch(ValidateException e){
                    	throw e;
                    } catch (Exception e) {
                        // Ignore
                    }
                }
                boolean falg = true;
                if(validate!=null){
                	falg = validate.validate(object,rowNum);
                }
                if(falg){
            		dataList.add((T) object);
            	}
            }
        }
    }

    /**
     * @project parse excel version 2003
     * @author jindz
     * @param <T>
     * @param
     * @throws Exception
     * 
     * @createDate 2015-9-22 13:28:22
     * 
     */
    private static <T> void parseSheetBy2003(ExcelValidate validate,HSSFSheet xssfSheet, Class<?> classz, List<T> dataList, File file, int startLine)
            throws Exception {
        if (xssfSheet == null) {
            return;
        }
        for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
            HSSFRow xssfRow = xssfSheet.getRow(rowNum);
            if (xssfRow == null || rowNum < startLine || isNullRow(xssfRow)) {
                continue;
            }
            Object object = getType(classz).newInstance();
            List<Method> method = getmethodList("get", getType(classz).getMethods());
            for (int i = 0; i < method.size(); i++) {
                try {
                	Field field = getFieldByMethod(method.get(i), object);
                    if (field.isAnnotationPresent(Excel.class)) {
                        Map map = parseComment(field);
                        int cell = toInt(map.get("index"));
                        String value = getValue(xssfRow.getCell(cell));
                        Method setMethod = getType(classz).getDeclaredMethod("set" + method.get(i).getName().split("get")[1],
                                method.get(i).getReturnType());
                        setMethod.invoke(object, generateParameter(value, method.get(i)));
                    }
                } catch (Exception e) {
                    // Ignore
                }
            }
            boolean falg = true;
            if(validate!=null){
            	falg = validate.validate(object,rowNum);
            }
            if(falg){
        		dataList.add((T) object);
        	}
        }
    }

    /**
     * 参数类型封装
     * 
     * @param valueStr
     * @param method
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private static Object generateParameter(String valueStr, Method method) {

        Object valObj = null;
        Class claz = method.getReturnType();
        try {
            if (claz.getName().equals(Timestamp.class.getName())) {
                valObj = new Timestamp(DateUtil.toDateByFormat(valueStr, "yyyy-MM-dd HH:mm:ss").getTime());
            } else if (claz.getName().equals(Date.class.getName())) {
                valObj = DateUtil.toDateByFormat(valueStr, "yyyy-MM-dd HH:mm:ss");
            } else {
                Constructor cons = claz.getDeclaredConstructor(String.class);
                valObj = cons.newInstance(valueStr);
            }
        } catch (Exception e) {
//            System.out .println("无法识别或封装的类型:[" + claz + "],方法:[" + method + "],值[" + valueStr + "],error:[" + e.getMessage() + "]");
        }

        return valObj;
    }

    private static boolean isNullRow(Object row) {
        if (row instanceof XSSFRow) {
            // 2007
            return isNullRow((XSSFRow) row);
        } else if (row instanceof HSSFRow) {
            // 2003
            return isNullRow((HSSFRow) row);
        }
        return true;
    }
    
    private static boolean isNullRow(XSSFRow row) {
        boolean isNullRow = true;
        try {
            int lastNum = row.getLastCellNum();
            for (int i = 0; i < lastNum; i++) {
                if ( !StringUtils.isEmpty(getValue(row.getCell(i)))  ) {
                    isNullRow = false;
                    break;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return isNullRow;
    }

    private static boolean isNullRow(HSSFRow row) {
        boolean isNullRow = true;
        try {
            int lastNum = row.getLastCellNum();
            for (int i = 0; i < lastNum; i++) {
                if (!StringUtils.isEmpty(getValue(row.getCell(i)))) {
                    isNullRow = false;
                    break;
                }
            }
        } catch (Exception e) {
            // Ignore
        }
        return isNullRow;
    }

    private static String getValue(Object cell) {

        if (cell instanceof XSSFCell) {
            // 2007
            return getValue(((XSSFCell) cell));
        } else if (cell instanceof HSSFCell) {
            // 2003
            return getValue(((HSSFCell) cell));
        }
        return null;

    }

    private static String getValue(HSSFCell hssfCell) {
        try {
            short format = hssfCell.getCellStyle().getDataFormat();
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58 || format == 176) {
                // 日期
                sdf = new SimpleDateFormat(FORMAT_CALENDAR);
            } else if (format == 20 || format == 32) {
                // 时间
                sdf = new SimpleDateFormat(FORMAT_TIME);
            } else {
                hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                return String.valueOf(hssfCell.getStringCellValue());
            }
            Double value = null;
            try {
                value = hssfCell.getNumericCellValue();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                hssfCell.setCellType(Cell.CELL_TYPE_STRING);
                return String.valueOf(hssfCell.getStringCellValue());
            }
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            return sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return null;
    }

    private static String getValue(XSSFCell xssfCell) {
        try {
            short format = xssfCell.getCellStyle().getDataFormat();
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58 || format == 176) {
                // 日期
                sdf = new SimpleDateFormat(FORMAT_CALENDAR);
            } else if (format == 20 || format == 32) {
                // 时间
                sdf = new SimpleDateFormat(FORMAT_TIME);
            } else {
                xssfCell.setCellType(Cell.CELL_TYPE_STRING);
                return String.valueOf(xssfCell.getStringCellValue());
            }
            Double value = null;
            try {
                value = xssfCell.getNumericCellValue();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                // e.printStackTrace();
//                System.out.println("Cannot get a numeric value from a text cell:" + xssfCell.toString());
                xssfCell.setCellType(Cell.CELL_TYPE_STRING);
                return String.valueOf(xssfCell.getStringCellValue());
            }
            Date date = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(value);
            return sdf.format(date);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        }
        return null;

    }

    private static void setText(XSSFWorkbook xssBook, XSSFSheet sheet, int row, String title, int titleIndex, String value,
            Integer valueType, String timeFormat, String calendarFormat) throws Exception {
        if (row == 0) {
            if (sheet.getRow(0) != null) {
                sheet.getRow(0).createCell(titleIndex).setCellValue(title);
            } else {
                sheet.createRow(0).createCell(titleIndex).setCellValue(title);
            }
        }
        XSSFCell cell = null;
        if (sheet.getRow(row + 1) != null) {
            cell = sheet.getRow(row + 1).createCell(titleIndex);
        } else {
            cell = sheet.createRow(row + 1).createCell(titleIndex);
        }
        try {
            switch (valueType) {
                case Excel.calendar:
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateUtil.toDateByFormat(value, calendarFormat));
                    cell.setCellValue(calendar);
                    break;
                case Excel.time:
                    Date date = DateUtil.toDateByFormat(value, timeFormat);
                    cell.setCellValue(date);
                    break;
                case Excel.number:
                    try {
                        cell.setCellValue(toInt(value));
                    } catch (Exception e) {
                        cell.setCellValue(Double.valueOf(value));
                    }
                    break;
                case Excel.string:
                    cell.setCellValue(value);
                    break;
                default:
                    cell.setCellValue(value);
            }
        } catch (Exception e) {
            cell.setCellValue(value);
            // e.printStackTrace();
        }
    }

    private static void setStype(Map<String, Object> comment, XSSFWorkbook xssBook, XSSFSheet sheet, int row, int titleIndex) {
        short border = Short.valueOf(comment.get("border") + "");
        Integer valueType = toInt(comment.get("textType"));
        short backgroundColor = Short.valueOf(comment.get("backgroundColor") + "");
        String timeFormat = toString(comment.get("timeFormat"));
        String calendarFormat = toString(comment.get("CalendarFormat"));

        // head stype
        if (row == 0) {
            XSSFCellStyle headStype = xssBook.createCellStyle();
            setBorder(headStype, border);
            XSSFFont font = xssBook.createFont();
            font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
            headStype.setFont(font);
            sheet.getRow(0).getCell(titleIndex).setCellStyle(headStype);
        }

        // text stype
        XSSFCellStyle textStype = xssBook.createCellStyle();
        setBorder(textStype, border);
        if (backgroundColor != Excel.defaultBackgroundColor) {
            textStype.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            textStype.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            textStype.setFillForegroundColor(backgroundColor);
        }

        XSSFCell cell = sheet.getRow(row + 1).getCell(titleIndex);
        XSSFDataFormat format = xssBook.createDataFormat();
        try {
            switch (valueType) {
                case Excel.calendar:
                    textStype.setDataFormat(format.getFormat(calendarFormat));
                    break;
                case Excel.time:
                    textStype.setDataFormat(format.getFormat(timeFormat));
                    break;
                case Excel.number:
                    // When you need to improve
                    break;
                case Excel.string:
                    // When you need to improve
                    break;
            }
        } catch (Exception e) {
            // e.printStackTrace();
        }
        cell.setCellStyle(textStype);
    }

    private static Map<String, Object> parseComment(Field filed) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		Annotation p = filed.getAnnotation(Excel.class);
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

    private static List<Method> getmethodList(String flag, Method[] method) {
        List<Method> methodList = new ArrayList<Method>();
        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().indexOf(flag) != -1) {
                methodList.add(method[i]);
            }
        }
        return methodList;
    }

    private static void setBorder(XSSFCellStyle stype, short border) {
        stype.setBorderTop(border);
        stype.setBorderBottom(border);
        stype.setBorderLeft(border);
        stype.setBorderRight(border);
        stype.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    }

    private static Class<?> getType(Class<?> classz) {
        // Class <?> entityClass = (Class <?>) ((ParameterizedType)
        // getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        // log.info("create Excel by entityClass:{}",entityClass.getName());
        // return entityClass;
        return classz;
    }
    
    public static String toString(Object obj) {
		if (obj != null) {
			return String.valueOf(obj).trim();
		}
		return "";
	}

	public static Integer toInt(Object obj) {
		if (obj != null) {
			return Integer.valueOf(toString(obj));
		}
		return null;
	}

}
