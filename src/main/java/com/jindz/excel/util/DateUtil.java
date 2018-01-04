
package com.jindz.excel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 时间工具类
 * 
 * DateUtil.java</br>
 * 
 * @author jindz</br>
 * @date 2016年12月19日 下午4:51:30</br>
 *
 */
public class DateUtil {

    public static final int SECOND = 1000;

    public static final int MINUTE = SECOND * 60;

    public static final int HOUR = MINUTE * 60;

    public static final int DAY = HOUR * 24;

    public static final int WEEK = DAY * 7;

    public static final int YEAR = DAY * 365;

    private static final String DEF_OUT_FORMAT = "yyyy-MM-dd";

    private static final String DATETIMEFORMATTER1 = "yyyy-MM-dd HH:mm:ss";

    private static final String DATETIMEFORMATTER = "yyyy/MM/dd HH:mm:ss";

    private static long MILLIONSECONDSOFDAY = 86400000;

    /**
     * 得到日期的前后几天
     *
     * @param date 日期
     * @param i    前后天数
     * @return 日期
     */
    public static Date getDate(Date date, int i) {
        // long seconds = date.getTime() + i * 86400000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);

        return calendar.getTime();

    }

    /**
     * 得到日期的前后小时
     *
     * @param date 日期
     * @param hour 日期追加几小时
     * @return 追加后的日期
     */
    public static Date getDateByHour(Date date, int hour) {
        // long seconds = date.getTime() + i * 86400000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);

        return calendar.getTime();

    }

    /**
     * 得到日期的前后分钟
     *
     * @param date 日期
     * @param min  日期追加几分钟
     * @return 追加后的日期
     */
    public static Date getDateByMinute(Date date, int min) {
        // long seconds = date.getTime() + i * 86400000;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);

        return calendar.getTime();

    }

    /**
     * 得到两个日期之间的天数,两头不算,取出数据后，可以根据需要再加
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期间隔的天数
     */
    public static int getDay(Date date1, Date date2) {
        return (int) ((date2.getTime() - date1.getTime()) / MILLIONSECONDSOFDAY);
    }

    /**
     * 取出2个日期间的天数,只要日期有间隔就算1天
     *
     * @param from 开始日期
     * @param to   结束日期
     * @return 间隔天数
     */
    public static int getDaysBetween(Date from, Date to) {
        Calendar d1 = Calendar.getInstance();
        d1.setTime(from);
        Calendar d2 = Calendar.getInstance();
        d2.setTime(to);
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            d1 = (Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 得到两个日期之间的天数,两头不算,取出数据后，可以根据需要再加 date2-date1
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期相隔的天数
     */
    public static int getDaysBetweenDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return (int) (c2.getTimeInMillis() - c1.getTimeInMillis()) / (60000 * 60 * 24);
    }

    /**
     * 得到两个日期之间的小时数,两头不算,取出数据后，可以根据需要再加 date2-date1
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期相隔几个小时
     */
    public static int getHoursBetweenDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return (int) (c2.getTimeInMillis() - c1.getTimeInMillis()) / (60000 * 60);
    }

    /**
     * 得到两个日期之间的分钟数，四舍五入
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期相隔的分钟数
     */
    public static int getRoundMinituesBetweenDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        float a = (float) (c2.getTimeInMillis() - c1.getTimeInMillis());
        int b = (int) (a / (60000));
        int c = (int) (a % (60000));
        if (c > 30000) {
            b = b + 1;
        }
        // BigDecimal d =
        // BigDecimal.valueOf(a).divide(BigDecimal.valueOf(60000), new
        // MathContext(2));
        return b;
    }

    /**
     * 得到两个日期之间的分钟数,两头不算,取出数据后，可以根据需要再加 date2-date1
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期相隔的分钟数
     */
    public static int getMinituesBetweenDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return (int) (c2.getTimeInMillis() - c1.getTimeInMillis()) / (60000);
    }

    /**
     * 得到两个日期之间的秒数,两头不算,取出数据后，可以根据需要再加 date2-date1
     *
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 两日期相隔的秒数
     */
    public static int getSecondsBetweenDate(Date date1, Date date2) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);
        return (int) (c2.getTimeInMillis() - c1.getTimeInMillis()) / (1000);
    }

    /**
     * addMonth个月后的同一天，如果当前日，大约目标月的最大日，则制定系统将调整成目标月的最后一天
     *
     * @param date 当前日期
     * @param min  在当前日期上增加月数
     * @return 增加月数后的日期
     */
    public static Date addMinitues(Date date, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * addMonth个月后的同一天，如果当前日，大约目标月的最大日，则制定系统将调整成目标月的最后一天
     *
     * @param date 当前日期
     * @param hour 当前日期上加上小时
     * @return 增加小时后的日期
     */
    public static Date addHours(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 根据日期取出是星期几
     *
     * @param date Date
     * @return int 返回1-7
     */
    public static int getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return (calendar.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * <p>
     * 根据 年 月 得到这个月的天数。
     * </p>
     *
     * @param year  指定年
     * @param month 指定月
     * @return 所指定月的天数
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int monthDays = calendar.getActualMaximum(Calendar.DATE);
        return monthDays;
    }

    /**
     * <p>
     * 获取指定日期的天数。
     * </p>
     *
     * @param date 指定日期
     * @return 指定日期的天数
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * <p>
     * 获得指定日期的月份。
     * </p>
     *
     * @param date 日期
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static int getYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static int getMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static java.sql.Date getSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * 取得没有时间的日期格式为 yyyy-mm-dd
     *
     * @param date Date
     * @return Date
     */
    public static Date getDate(Date date) {
        String str = dateToString(date);
        return getDate(str);

    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date1 date1
     * @param date2 date2
     * @return return
     */
    public static List<Date> getDateList(Date date1, Date date2) {
        int day = getDay(date1, date2);

        List<Date> dates = new ArrayList<Date>();

        for (int i = 0; i <= day; i++) {
            Date date = getDate(date1, i);
            dates.add(date);
        }
        return dates;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date1 date1
     * @param date2 date2
     * @return return
     */
    public static List<String> getDates(Date date1, Date date2) {
        int day = getDay(date1, date2);

        List<String> dates = new ArrayList<String>();

        for (int i = 0; i <= day; i++) {
            Date date = getDate(date1, i);
            dates.add(toStringByFormat(date, "yyyy-MM-dd"));
        }
        return dates;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date1  date1
     * @param date2  date2
     * @param format format
     * @return return
     */
    public static List<String> getFormatDates(Date date1, Date date2, String format) {
        int day = getDay(date1, date2);

        List<String> dates = new ArrayList<String>();

        for (int i = 0; i <= day; i++) {
            Date date = getDate(date1, i);
            dates.add(toStringByFormat(date, format));
        }
        return dates;
    }

    /**
     * 产生唯一日期时间序列
     *
     * @param
     * @return String
     */
    public static String getUniueTimeString() {
        return String.valueOf(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * <p>
     * 日期类型转换为字符串类型。
     * </p>
     *
     * @param date 日期
     * @return 日期字符串
     */
    public static String dateToString2(Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String tmpStr = "";
        if (date != null) {
            tmpStr = sdf.format(date);
        }

        return tmpStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static String datetimeToStringHour(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
        String tmpStr = "";
        if (date != null) {
            tmpStr = sdf.format(date);
        }

        return tmpStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static String datetimeToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String tmpStr = "";
        if (date != null) {
            tmpStr = sdf.format(date);
        }

        return tmpStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date date
     * @return return
     */
    public static String datetimesToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DATETIMEFORMATTER1);
        String tmpStr = "";
        if (date != null) {
            tmpStr = sdf.format(date);
        }

        return tmpStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date   date
     * @param format format
     * @return return
     */
    public static String toStringByFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String tmpStr = "";
        if (date != null) {
            tmpStr = sdf.format(date);
        }

        return tmpStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param str    str
     * @param format format
     * @return return
     */
    public static Date toDateByFormat(String str, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 日期字符类型转换为日期格式类型。
     * </p>
     *
     * @param str 日期字符串
     * @return 转换后的日期
     */
    public static Date stringToDate(String str) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param str str
     * @return return
     */
    public static Date stringToDateTime(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param str str
     * @return return
     */
    public static Date stringToDatetime(String str) {
        if (str == null) {
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            return null;
        }
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param s s
     * @return return
     * @throws ParseException 异常
     */
    public static Date strtodate1(String s) throws ParseException {
        try {
            Date date = new Date(new SimpleDateFormat(DATETIMEFORMATTER1).parse(s).getTime());
            return date;
        } catch (ParseException e) {
            // e.printStackTrace();
            Date date1 = new Date(new SimpleDateFormat(DATETIMEFORMATTER).parse(s).getTime());
            return date1;
        }

    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param s str
     * @return return
     */
    public static java.sql.Date strtosqldate1(String s) {
        try {
            java.sql.Date date = new java.sql.Date(new SimpleDateFormat(DATETIMEFORMATTER1).parse(s).getTime());
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            java.sql.Date date1 = new java.sql.Date((new Date()).getTime());
            return date1;
        }

    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param str str
     * @return return
     */
    public static Date getDate(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
            return null;
        }

    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param str str
     * @return return
     */
    public static boolean isDate(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            sdf.parse(str);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param duration duration
     * @return return
     */
    public static String getTimeString(int duration) {
        int hours = duration / DateUtil.HOUR;
        int remain = duration - (hours * DateUtil.HOUR);
        int minutes = remain / DateUtil.MINUTE;
        StringBuffer time = new StringBuffer(64);
        if (hours != 0) {
            if (hours == 1) {
                time.append("1 hour and ");
            } else {
                time.append(hours).append(" hours and ");
            }
        }
        if (minutes == 1) {
            time.append("1 minute");
        } else {
            // what if minutes == 0 ???
            time.append(minutes).append(" minute(s)");
        }
        return time.toString();
    }

    /**
     * <p>
     * 日期比较小于等于。
     * </p>
     *
     * @param f date1
     * @param t date2
     * @return reuturn
     */
    public static boolean beforeEqual(Date f, Date t) {
        return f.compareTo(t) <= 0;
    }

    /**
     * <p>
     * 日期比较 大于等于。
     * </p>
     *
     * @param f date1
     * @param t date2
     * @return reuturn
     */
    public static boolean afterEqual(Date f, Date t) {
        return f.compareTo(t) >= 0;
    }

    /**
     * <p>
     * 日期比较 等于。
     * </p>
     *
     * @param f date1
     * @param t date2
     * @return reuturn
     */
    public static boolean compareEqual(Date f, Date t) {
        return f.compareTo(t) == 0;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param d date1
     * @param s date2
     * @param e date3
     * @return return
     */
    public static boolean between(Date d, Date s, Date e) {
        return d.before(e) && d.after(s);
    }

    /**
     * 获得当天日期
     *
     * @return yyyy-mm-dd
     */
    public static String getCurrentDateStr() {
        String curDateStr = "";

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        curDateStr = String.valueOf(year) + "-";
        curDateStr += ((month < 10) ? "0" + String.valueOf(month) : String.valueOf(month)) + "-";
        curDateStr += ((day < 10) ? "0" + String.valueOf(day) : String.valueOf(day));

        return curDateStr;
    }

    /**
     * 获得当天月份
     *
     * @return yyyy-mm
     */
    public static String getCurrentMonthStr() {
        String curDateStr = "";

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        curDateStr = String.valueOf(year) + "-";
        curDateStr += ((month < 10) ? "0" + String.valueOf(month) : String.valueOf(month));

        return curDateStr;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date      date
     * @param outFormat outformat
     * @return return
     */
    public static String format(Date date, String outFormat) {
        if (date == null) {
            return "";
        }
        return format(date, outFormat, Locale.ENGLISH);
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date      date
     * @param outFormat outformat
     * @param locale    locale
     * @return return
     */
    public static String format(Date date, String outFormat, Locale locale) {
        if (outFormat == null || "".compareTo(outFormat) == 0) {
            outFormat = DEF_OUT_FORMAT;
        }
        SimpleDateFormat outDateFormat = null;
        if (locale == null) {
            outDateFormat = new SimpleDateFormat(outFormat, Locale.ENGLISH);
        } else {
            outDateFormat = new SimpleDateFormat(outFormat, locale);
        }
        String outDate = outDateFormat.format(date);
        return outDate;
    }

    /**
     * <p>
     * 功能的简单描述，参数、返回值及异常必须注明。
     * </p>
     *
     * @param date     date
     * @param interval interval
     * @return return
     */
    public static Date getBeforetime(Date date, int interval) {
        GregorianCalendar gca = new GregorianCalendar();
        gca.setTime(date);
        gca.add(GregorianCalendar.MONTH, (-1) * interval);
        return gca.getTime();
    }

    /**
     * <p>
     * // 得到当前时间对应的前一个月时间。
     * </p>
     *
     * @param date date
     * @return reutrn
     */
    public static String getBeforetime(Date date) {
        return format(getBeforetime(date, 1), DEF_OUT_FORMAT);
    }

    /**
     * 得到当前时间对应的之后第i个月的时间,yyyy-mm-dd.
     *
     * @param date date
     * @param i    i
     * @return return
     */
    public static String getTargetBeforeTime(Date date, int i) {
        return format(getBeforetime(date, i), DEF_OUT_FORMAT);
    }

    /**
     * <p>
     * // 得到这个传入月份的第一天 （格式为YYYY-MM-dd）。
     * </p>
     *
     * @param dateString dateString
     * @return return
     */
    public static Date getFirstDateOfMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        Calendar ca = Calendar.getInstance();
        if (date != null) {
            ca.setTime(date);
        }
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * <p>
     * // 得到这个传入月份的最后一天（格式为YYYY-MM-dd）。
     * </p>
     *
     * @param dateString dateString
     * @return return
     */
    public static Date getEndDateOfMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        Calendar ca = Calendar.getInstance();
        if (date != null) {
            ca.setTime(date);
        }
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * <p>
     * // 得到传入月份上个月的第一天（格式为YYYY-MM-dd）。
     * </p>
     *
     * @param dateString datestring
     * @return return
     */
    public static Date getFirstDateOfPreMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        Calendar ca = Calendar.getInstance();
        GregorianCalendar gca = new GregorianCalendar();
        if (date != null) {
            gca.setTime(date);
        }
        gca.add(GregorianCalendar.MONTH, (-1) * 1);
        ca.setTime(gca.getTime());
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMinimum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * <p>
     * 得到传入月份上个月的最后一天 （格式为YYYY-MM-dd）。
     * </p>
     *
     * @param dateString datestring
     * @return return
     */
    public static Date getEndDateOfPreMonth(String dateString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace(); // To change body of catch statement use File |
            // Settings | File Templates.
        }
        Calendar ca = Calendar.getInstance();
        GregorianCalendar gca = new GregorianCalendar();
        if (date != null) {
            gca.setTime(date);
        }
        gca.add(GregorianCalendar.MONTH, (-1) * 1);
        ca.setTime(gca.getTime());
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
    }

    /**
     * 根据传入的日期，得到年和月拼装成的int
     *
     * @param date date
     * @return 年和月拼装成的int
     */
    public static int getCurrentDateMonthInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR) * 100 + calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * addMonth个月后的同一天，如果当前日，大约目标月的最大日，则制定系统将调整成目标月的最后一天
     *
     * @param date     date
     * @param addMonth addmonth
     * @return return
     */
    public static Date addMonths(Date date, int addMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, addMonth);
        return calendar.getTime();
    }

    /**
     * 指定日期之后的最近target日的那天，返回yyyy-MM-dd格式的日期
     *
     * @param date      date
     * @param targetDay targetday
     * @return return
     */
    public static Date nextNearestDay(Date date, int targetDay) {
        if (targetDay < 0 || targetDay > 28) {
            return null;
        }
        date = getDate(date);
        int day = getDay(date);
        if (day > targetDay) {
            date = addMonths(date, 1);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, targetDay);
        return calendar.getTime();

    }

    /**
     * <p>
     * 测试。
     * </p>
     *
     * @param args 参数
     */
    public static void main(String[] args) {
//        Date d1 = DateUtil.getDate("2009-08-01");
//        Date d2 = DateUtil.addMonths(d1, 1);
//        System.out.println(DateUtil.datetimeToStringHour(d2));

        System.out.println(DateUtil.getDate(new Date(), 1));

//        String str = "1970-07-10T16:00:35 GMT+08:00";
////        str = "0035-07-10T00:16:27 GMT+08:00";
//        System.out.println(convert(str, "yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));

//        try {
//            String datdString = "Wed Oct 12 2016 00:42:00 GMT+0800 (中国标准时间)";
//            datdString = datdString.replace("GMT", "").replaceAll("\\(.*\\)", "");
//            //将字符串转化为date类型，格式2016-10-12
//            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss z", Locale.ENGLISH);
//            Date dateTrans = format.parse(datdString);
//            System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTrans));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        String source = "20 Feb 2012 11:38:40 GMT";
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String dest = format.format(Date.parse(source));
//        System.out.println(dest);

    }

    /**
     * 根据日期类型转换日期字符格式类型
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        String data = sf.format(date);
        return data;
    }

    /**
     * <p>
     * 将字符日期从指定格式转换到另一种字符日期
     * </p>
     *
     * @param inDateStr  需要转换日期串
     * @param inPattern  需要转换的日期串的格式
     * @param outPattern 转换后输出的格式
     * @return 转成字符日期成outPattern模式的字符日期
     */
    public static String convert(String inDateStr, String inPattern, String outPattern) {
        if (inDateStr == null || inPattern == null || outPattern == null) {
            return "";
        }
        try {
            Date tmp = new SimpleDateFormat(inPattern).parse(inDateStr);
            return new SimpleDateFormat(outPattern).format(tmp);
        } catch (ParseException e) {
            return inDateStr;
        }
    }

}
