package com.zt.blog.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 处理时间的通用类
 * 
 *               Created on Nov 2, 2010 by liuzhaoq
 * 
 */
public class DateUtil {
    private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static String YEARMMDD_FORMAT = "yyyy-MM-dd";

    public static String YEARMM_FORMAT = "yyyy-MM";

    public static String YEARMMDD_HHMMSS_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static String YEARMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";

    private static String YEARMMDD_DATE_FORMAT = "yyyyMMdd";

    private static String HHMMSS_FORMAT = "HH:mm:ss";
    public static final String DATE_TIMEZONE = "GMT+8";

    public static SimpleDateFormat YEARMMDD_HHMMSS = new SimpleDateFormat(YEARMMDD_HHMMSS_FORMAT);

    public static SimpleDateFormat YEARMMDD = new SimpleDateFormat(YEARMMDD_FORMAT);

    public static SimpleDateFormat YEARMM = new SimpleDateFormat(YEARMM_FORMAT);

    public static SimpleDateFormat YEARMMDDHHMMSS = new SimpleDateFormat(YEARMMDDHHMMSS_FORMAT);

    public static SimpleDateFormat YEARMMDDATA = new SimpleDateFormat(YEARMMDD_DATE_FORMAT);

    public static SimpleDateFormat CHINESE_FORMAT = new SimpleDateFormat("MM月dd日 kk点mm分");

    public static SimpleDateFormat CHINESE_FORMAT_YEAR = new SimpleDateFormat("yyyy年MM月dd日 kk点mm分");

    public static final Calendar CALENDAR = Calendar.getInstance();

    public static synchronized Date createDate(final int year, final int month, final int day, final int hour,
	    final int minute, final int second, final int millisecond) {
	CALENDAR.clear();
	CALENDAR.set(year, month - 1, day, hour, minute, second);
	CALENDAR.set(Calendar.MILLISECOND, millisecond);
	return CALENDAR.getTime();
    }

    public static synchronized Date createDate(final int year, final int month, final int day) {
	createDate(year, month, day, 0, 0, 0, 0);
	return CALENDAR.getTime();
    }

    public static synchronized Date createDate(final int year, final int month, final int day, final int hour) {
	createDate(year, month, day, hour, 0, 0, 0);
	return CALENDAR.getTime();
    }

    public static synchronized Date createDate(final int year, final int month, final int day, final int hour,
	    final int minute) {
	createDate(year, month, day, hour, minute, 0, 0);
	return CALENDAR.getTime();
    }

    public static synchronized Date createDate(final int year, final int month, final int day, final int hour,
	    final int minute, final int second) {
	createDate(year, month, day, hour, minute, second, 0);
	return CALENDAR.getTime();
    }

    // 将时间戳转为字符串
    public static String getStrTime(long cc_time) {
	String re_StrTime = null;
	SimpleDateFormat sdf = new SimpleDateFormat(YEARMMDD_HHMMSS_FORMAT);
	re_StrTime = sdf.format(new Date(cc_time));
	return re_StrTime;
    }

    /**
     * 判断是否在两个时间段之间 比较时间格式 yyyy-MM-dd HH:mm:ss
     * 
     * @param startTime
     *            开始时间
     * @param nowTime
     *            比较时间
     * @param endTime
     *            结束时间
     * @return
     */
    public static boolean isExchange(String startTime, String nowTime, String endTime) {
	if (startTime == null || endTime == null)
	    return false;
	Date startDate = null;
	Date endDate = null;
	Date nowDate = null;
	try {
	    startDate = YEARMMDD_HHMMSS.parse(startTime);
	    endDate = YEARMMDD_HHMMSS.parse(endTime);
	    if (nowTime == null || "".equals(nowTime)) {
		nowDate = getNowDate();
	    } else {
		nowDate = YEARMMDD_HHMMSS.parse(nowTime);
	    }
	} catch (Exception e) {
	}
	return nowDate.after(startDate) && nowDate.before(endDate);
    }

    /**
     * 判断当前时间在两个时间之内 与当前时间相比
     * 
     * @param startTime
     *            开始时间
     * @param endTime
     *            结束时间
     * @return
     */
    public static boolean isExchange(String startTime, String endTime) {
	return isExchange(startTime, null, endTime);
    }

    /**
     * 判断时间是否是当天
     * 
     * @param startTime
     * @return
     */
    public static boolean isEqual(String startTime, String nowTime) {
	Date startDate = null;
	Date nowDate = null;
	try {
	    startDate = YEARMMDD.parse(startTime);
	    if (nowTime == null || "".equals(nowTime)) {
		nowDate = getNowDateShort();
	    } else {
		nowDate = YEARMMDD.parse(nowTime);
	    }
	} catch (Exception e) {
	}
	return startDate.compareTo(nowDate) == 0 ? true : false;
    }

    public static boolean isEqual(String startTime) {
	return isEqual(startTime, null);
    }

    /**
     * 判断是否是同一天
     * 
     * @param startTime
     *            yyyy-MM-dd
     * @param endTime
     *            yyyy-MM-dd
     * @return
     */
    public static boolean isSameToday(Date startTime, Date endTime) {
	if (startTime == null)
	    return false;
	try {
	    String startTimeStr = YEARMMDD.format(startTime);
	    String endTimeStr = "";
	    if (endTime == null) {
		endTimeStr = getNowStrShort();
	    } else {
		endTimeStr = YEARMMDD.format(endTime);
	    }
	    return startTimeStr.equals(endTimeStr) ? true : false;
	} catch (Exception e) {
	    return false;
	}
    }

    public static boolean isSameToday(Date startTime) {
	return isSameToday(startTime, null);
    }

    public static String chineseMD() {
	Date now = new Date();
	return CHINESE_FORMAT.format(now);
    }

    public static String chineseYMD() {
	Date now = new Date();
	return CHINESE_FORMAT_YEAR.format(now);
    }

    /**
     * 获取现在时间
     * 
     */
    public static Date getNowDateShort() {
	Date currentTime = new Date();
	String dateString = YEARMMDD.format(currentTime);
	ParsePosition pos = new ParsePosition(8);
	Date currentTime_2 = YEARMMDD.parse(dateString, pos);
	return currentTime_2;
    }

    /**
     * 计算两个日期之间相差的天数
     * 
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	smdate = sdf.parse(sdf.format(smdate));
	bdate = sdf.parse(sdf.format(bdate));
	Calendar cal = Calendar.getInstance();
	cal.setTime(smdate);
	long time1 = cal.getTimeInMillis();
	cal.setTime(bdate);
	long time2 = cal.getTimeInMillis();
	long between_days = (time2 - time1) / (1000 * 3600 * 24);

	return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Calendar cal = Calendar.getInstance();
	cal.setTime(sdf.parse(smdate));
	long time1 = cal.getTimeInMillis();
	cal.setTime(sdf.parse(bdate));
	long time2 = cal.getTimeInMillis();
	long between_days = (time2 - time1) / (1000 * 3600 * 24);

	return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取现在时间
     * 
     * @return返回短时间格式 yyyy-MM-dd
     */
    public static String getNowStrShort() {
	Date currentTime = new Date();
	String dateString = YEARMMDD.format(currentTime);
	return dateString;
    }

    /**
     * 获取当日
     * 
     * @return 返回时间格式yyyyMMdd
     */
    public static String getToday() {
	Date currentTime = new Date();
	String dateString = YEARMMDDATA.format(currentTime);
	return dateString;
    }

    /**
     * 获取现在时间
     * 
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
	Date currentTime = new Date();
	return currentTime;
    }

    /**
     * 
     * @param timeStr
     * @return
     */
    public static Date getDate(String timeStr) {
	try {
	    return YEARMMDDHHMMSS.parse(timeStr);
	} catch (Exception e) {
	}
	return new Date();
    }

    /**
     * 获取当前时间
     * 
     * @return返回短时间格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowStrDate() {
	Date currentTime = new Date();
	String dateString = YEARMMDD_HHMMSS.format(currentTime);
	return dateString;
    }

    /**
     * 获取当前时间
     * 
     * @return返回短时间格式 yyyyMMddHHmmss
     */
    public static String getNowYDate() {
	Date currentTime = new Date();
	String dateString = YEARMMDDHHMMSS.format(currentTime);
	return dateString;
    }

    /**
     * 获取当前的小时
     * 
     * @return
     */
    public static int getNowHour() {
	Calendar ca = Calendar.getInstance();
	int hour = ca.get(Calendar.HOUR_OF_DAY);// 小时
	return hour;
    }

    public static int getNowMonth() {
	Calendar ca = Calendar.getInstance();
	int month = ca.get(Calendar.MONTH) + 1;// 月份
	return month;
    }

    /**
     * 获取当前月中的天数
     * 
     * @return
     */
    public static int getNowDayInMonth() {
	Calendar ca = Calendar.getInstance();
	int day = ca.get(Calendar.DAY_OF_MONTH);
	return day;
    }

    /**
     * 判断是否是在某个时间之前
     * 
     * @param endTime
     *            如:9点10分之前 2010-11-20 09:10:00
     * @return true false
     */
    public static boolean timeBefor(String endTime) {
	if (endTime == null) {
	    return false;
	}
	Date endDate = null;
	Date nowDate = getNowDate();
	try {
	    endDate = YEARMMDD_HHMMSS.parse(endTime);
	    nowDate = getNowDate();
	} catch (Exception e) {
	}
	return nowDate.before(endDate);
    }

    public static boolean timeBefor(String startTime, String endTime) {
	if (endTime == null) {
	    return false;
	}
	Date endDate = null;
	Date nowDate = getNowDate();
	try {
	    endDate = YEARMMDD_HHMMSS.parse(endTime);
	    if (startTime == null || "".equals(startTime)) {
		nowDate = getNowDate();
	    } else {
		nowDate = YEARMMDD_HHMMSS.parse(startTime);
	    }
	} catch (Exception e) {
	}
	return nowDate.before(endDate);
    }

    /**
     * 计算给定时间和当前时间 获取时间
     * 
     * @param startTime
     * @return
     */
    public static String calculateDate(String startTime) {
	StringBuffer str = new StringBuffer();
	Date nowDate = getNowDate();
	Date startDate = null;
	try {

	} catch (Exception e) {
	}
	return str.toString();
    }

    /**
     * 计算两个日期相差的天数
     */

    public static int calculate(String startTime, String endTime) {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	long to = 0;
	long from = 0;
	try {
	    to = df.parse(endTime).getTime();
	    from = df.parse(startTime).getTime();
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	int days = (int) ((to - from) / (1000 * 60 * 60 * 24));
	return days;
    }

    /**
     * 格式化日期返回指定日期样式字符串
     * 
     * @param aMask
     * @param aDate
     * @return
     */
    public static final String getDateTime(String aMask, Date aDate) {
	SimpleDateFormat df = null;
	String returnValue = "";

	if (aDate != null) {
	    df = new SimpleDateFormat(aMask);
	    returnValue = df.format(aDate);
	}

	return (returnValue);
    }

    /**
     * 格式化字符串返回日期类型
     * 
     * @param aMask
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate) throws ParseException {
	SimpleDateFormat df = null;
	Date date = null;
	df = new SimpleDateFormat(aMask);

	if (logger.isDebugEnabled()) {
	    logger.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
	}

	try {
	    date = df.parse(strDate);
	} catch (ParseException pe) {
	    // log.error("ParseException: " + pe);
	    throw new ParseException(pe.getMessage(), pe.getErrorOffset());
	}

	return date;
    }

    /**
     * 以"YYYY-MM-DD"格式取得日期字符串
     * 
     * @param dateTime
     * @return
     */
    public static String getDateString(Date dateTime) {
	return DateUtil.getDateTime(YEARMMDD_FORMAT, dateTime);
    }

    /**
     * 以"YYYY-MM"格式取得日期字符串
     * 
     * @param dateTime
     * @return
     */
    public static String getDateMonthString(Date dateTime) {
	return DateUtil.getDateTime(YEARMM_FORMAT, dateTime);
    }

    /**
     * 以"YYYY-MM-DD HH:mm:ss"格式取得日期字符串
     * 
     * @param dateTime
     * @return
     */
    public static String getDateTimeString(Date dateTime) {
	if (dateTime == null) {
	    dateTime = new Date();
	}
	return DateUtil.getDateTime(YEARMMDD_HHMMSS_FORMAT, dateTime);
    }

    /**
     * 以"YYYY-MM-DD HH:mm:ss"格式取得日期字符串
     * @return
     */
    public static String getDateTimeString() {
	Date nowDate = new Date();
	return getDateTimeString(nowDate);
    }

    /**
     * 处理不带时分的日期字符串，如"YYYY-MM-DD"
     * 
     * @return Date
     */
    public static Date getDateFromString(String dateTime) {
	if (StringUtils.isEmpty(dateTime))
	    return null;
	try {
	    return convertStringToDate(YEARMMDD_FORMAT, dateTime);
	} catch (ParseException pe) {
	    return null;
	}
    }

    /**
     * 处理不带时分的日期字符串，如"YYYY-MM-DD HH:mm:ss"
     * 
     * @return Date
     */
    public static Date getDateTimeFromString(String dateTime) {
	if (StringUtils.isEmpty(dateTime))
	    return null;
	try {
	    return convertStringToDate(YEARMMDD_HHMMSS_FORMAT, dateTime);
	} catch (ParseException pe) {
	    return null;
	}
    }

    /**
     * 计算多少年、多少月、多少天后的时间
     * 
     * @param date
     * @return
     */
    public static String caluateDate(String date, int year, int month, int day) {
	// 先使用SimpleDateFormat将时间格式化成日期，别忘了抛出异常。
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String finalDate = null;
	try {
	    Date resultDate = sdf.parse(date);
	    // 然后使用Calendar操作日期
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(resultDate);
	    calendar.add(Calendar.YEAR, year);
	    calendar.add(Calendar.MONTH, month);
	    calendar.add(Calendar.DATE, day);
	    // 把得到的日期格式化成字符串类型的时间
	    finalDate = sdf.format(calendar.getTime());
	} catch (ParseException e) {
	    e.printStackTrace();
	} // 抛异常
	return finalDate;
    }

    /**
     * 判断前一个日期是否早于后一个日期
     * 
     * @param sDate
     * @param eDate
     * @return
     */
    public static boolean before(Date sDate, Date eDate) {
	Calendar sCal = new GregorianCalendar();
	sCal.setTime(sDate);
	Calendar eCal = new GregorianCalendar();
	eCal.setTime(eDate);
	return sCal.before(eCal);
    }

    /**
     * 根据输入的时间,得到输入时间当天的起始时间,即当天时间的0:0:0
     * 
     * @param date
     * @return
     */
    public static Date getStartTimeOfDate(Date date) {
	if (date == null)
	    return null;
	Calendar cal = new GregorianCalendar();
	cal.setTime(date);
	cal.set(Calendar.AM_PM, Calendar.AM);
	cal.set(Calendar.HOUR, 0);
	cal.set(Calendar.MINUTE, 0);
	cal.set(Calendar.SECOND, 0);
	return cal.getTime();
    }

    /**
     * 根据输入的时间,得到输入时间当天的最后时间,即当天时间的23:59:59
     * 
     * @param date
     * @return
     */
    public static Date getEndTimeOfDate(Date date) {
	if (date == null)
	    return null;
	Calendar cal = new GregorianCalendar();
	cal.setTime(date);
	cal.set(Calendar.AM_PM, Calendar.PM);
	cal.set(Calendar.HOUR, 11);
	cal.set(Calendar.MINUTE, 59);
	cal.set(Calendar.SECOND, 59);
	return cal.getTime();
    }

    /**
     * 判断是否是每天的9:20-11:30 13:00-15:00点之间
     * 
     * @param date
     * @return
     */
    public static boolean checkExchangeHour(Date date) {
	if (date == null) {
	    throw new IllegalArgumentException("date is null");
	}

	boolean result = false;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String strDay = sdf.format(date);
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	try {
	    // 交易时间 9:30-11:30 13:00-15:00
	    if ((date.compareTo(sdf2.parse(strDay + " 09:20:00")) >= 0 && date.compareTo(sdf2.parse(strDay
		    + " 11:30:00")) <= 0)
		    || (date.compareTo(sdf2.parse(strDay + " 13:00:00")) >= 0 && date.compareTo(sdf2.parse(strDay
			    + " 15:10:00")) <= 0)) {
		result = true;
	    } else {
		result = false;
	    }
	} catch (ParseException e) {
	    throw new Error("date format error.");
	}

	return result;
    }

    /**
     * 几个小时之前的时间
     * 
     * @param hour
     * @return
     */
    public static Date beforeNowDate(int hour) {
	Calendar calendar = Calendar.getInstance();
	/* HOUR_OF_DAY 指示一天中的小时 */
	calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
	return calendar.getTime();
    }

    /**
     * 对时间的操作
     * 
     * @param minute
     *            分钟
     * @return
     */
    public static Date afterNowDate(int minute) {
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.MINUTE, minute);
	return calendar.getTime();
    }

    /**
     * 获取过去的分钟
     * 
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
	long t = new Date().getTime() - date.getTime();
	return t / (60 * 1000);
    }

    /**
     * 获取时间戳
     * 
     * @return
     */
    public static long getTimestamp() {
	long timestamp = System.currentTimeMillis() / 1000;
	return timestamp;
    }

    public static boolean checkDateFormate(String datetime) {
	String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

	Pattern pat = Pattern.compile(rexp);

	Matcher mat = pat.matcher(datetime);

	boolean dateType = mat.matches();
	return dateType;
    }

    public static final long MILLIS_PER_SECOND = 1000; // Number of milliseconds
						       // in a standard second.

    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND; // Number
									 // of
									 // milliseconds
									 // in a
									 // standard
									 // minute.

    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE; // Number
								       // of
								       // milliseconds
								       // in a
								       // standard
								       // hour.

    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR; // Number of
								    // milliseconds
								    // in a
								    // standard
								    // day.

    private static final int[] MONTH_LENGTH = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };


    /**
     * 是否同一时刻.
     */
    public static boolean isSameTime( final Date date1,  final Date date2) {
	// date.getMillisOf() 比date.getTime()快
	return date1.compareTo(date2) == 0;
    }

    /**
     * 判断日期是否在范围内，包含相等的日期
     */
    public static boolean isBetween( final Date date,  final Date start,  final Date end) {
	if (date == null || start == null || end == null || start.after(end)) {
	    throw new IllegalArgumentException("some date parameters is null or dateBein after dateEnd");
	}
	return !date.before(start) && !date.after(end);
    }

   

    // /// 获取日期的位置//////
    /**
     * 获得日期是一周的第几天. 已改为中国习惯，1 是Monday，而不是Sundays.
     */
    public static int getDayOfWeek( final Date date) {
	int result = get(date, Calendar.DAY_OF_WEEK);
	return result == 1 ? 7 : result - 1;
    }

    /**
     * 获得日期是一年的第几天，返回值从1开始
     */
    public static int getDayOfYear( final Date date) {
	return get(date, Calendar.DAY_OF_YEAR);
    }

    /**
     * 获得日期是一月的第几周，返回值从1开始.
     * 
     * 开始的一周，只要有一天在那个月里都算. 已改为中国习惯，1 是Monday，而不是Sunday
     */
    public static int getWeekOfMonth( final Date date) {
	return getWithMondayFirst(date, Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获得日期是一年的第几周，返回值从1开始.
     * 
     * 开始的一周，只要有一天在那一年里都算.已改为中国习惯，1 是Monday，而不是Sunday
     */
    public static int getWeekOfYear( final Date date) {
	return getWithMondayFirst(date, Calendar.WEEK_OF_YEAR);
    }

    private static int get(final Date date, int field) {
	Calendar cal = Calendar.getInstance();
	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setTime(date);

	return cal.get(field);
    }

    private static int getWithMondayFirst(final Date date, int field) {
	Calendar cal = Calendar.getInstance();
	cal.setFirstDayOfWeek(Calendar.MONDAY);
	cal.setTime(date);
	return cal.get(field);
    }




    // //// 闰年及每月天数///////
    /**
     * 是否闰年.
     */
    public static boolean isLeapYear( final Date date) {
	return isLeapYear(get(date, Calendar.YEAR));
    }

    /**
     * 是否闰年，移植Jodd Core的TimeUtil
     * 
     * 参数是公元计数, 如2016
     */
    public static boolean isLeapYear(int y) {
	boolean result = false;

	if (((y % 4) == 0) && // must be divisible by 4...
		((y < 1582) || // and either before reform year...
			((y % 100) != 0) || // or not a century...
		((y % 400) == 0))) { // or a multiple of 400...
	    result = true; // for leap year.
	}
	return result;
    }

    /**
     * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
     */
    public static int getMonthLength( final Date date) {
	int year = get(date, Calendar.YEAR);
	int month = get(date, Calendar.MONTH);
	return getMonthLength(year, month);
    }

    /**
     * 获取某个月有多少天, 考虑闰年等因数, 移植Jodd Core的TimeUtil
     */
    public static int getMonthLength(int year, int month) {

	if ((month < 1) || (month > 12)) {
	    throw new IllegalArgumentException("Invalid month: " + month);
	}
	if (month == 2) {
	    return isLeapYear(year) ? 29 : 28;
	}

	return MONTH_LENGTH[month];
    }


}
