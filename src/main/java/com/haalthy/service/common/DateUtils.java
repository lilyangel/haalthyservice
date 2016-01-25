package com.haalthy.service.common;

/**
 * Created by Ken on 2016-01-05.
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    public final static long ONEDAY = 24 * 60 * 60 * 1000; //一天的时间,    单位：毫秒
    public final static long ONEDAY_SECOND = 24 * 60 * 60; //一天,    单位：秒
    public final static long ONEHOUR = 1 * 60 * 60;        //一小时的时间,  单位：秒
    //public final static long ONE_MINUTE = 1 *  60;        //一分钟,  单位：秒
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    private static final long ONE_MINUTE = 60000L;
    private static final long ONE_HOUR = 3600000L;
    private static final long ONE_DAY = 86400000L;
    private static final long ONE_WEEK = 604800000L;

    private static final String ONE_SECOND_AGO = "秒前";
    private static final String ONE_MINUTE_AGO = "分钟前";
    private static final String ONE_HOUR_AGO = "小时前";
    private static final String ONE_DAY_AGO = "天前";
    private static final String ONE_MONTH_AGO = "月前";
    private static final String ONE_YEAR_AGO = "年前";
//
//    public static void main(String[] args) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
//        Date date = format.parse("2013-11-11 18:35:35");
//        System.out.println(format(date));
//    }


    public static long getLongDate(String date) {
        String str = date;
        String str1 = str.replace("-", "").substring(0, 8);
        String str2= str.substring(str.length()-9,str.length()).replace(":", "").substring(0, 7);
        String str3=str2.substring(str2.length()-6,str2.length());
        str=str1+str3;
        long intDate = Long.parseLong(str);
        return intDate;
    }

    public static String format(Date model) {
        long delta = new Date().getTime() - model.getTime();
        if (delta < 1L * ONE_MINUTE) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
        }
        if (delta < 45L * ONE_MINUTE) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
        }
        if (delta < 24L * ONE_HOUR) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
        }
        if (delta < 48L * ONE_HOUR) {
            return "昨天";
        }
        if (delta < 30L * ONE_DAY) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
        }
        if (delta < 12L * 4L * ONE_WEEK) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }




    private long nowDate = Long.valueOf(sdf.format(new Date()));


    public static Date addDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static String addDay2(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return sdf2.format(calendar.getTime());
    }

    public static String addDay(String dateString, int day) {
        Date date = parseDate(dateString);
        date = addDay(date, day);
        return sdf.format(date);
    }

    public static Date parseDate(String dateString) {
        try {
            return sdf2.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }


    /***
     * 将String字符串 转换成 Sting类型时间 ，格式为yyyyMMddHHmmss, 例：返回2009-10-10 15:30:30
     *
     * @param dateString
     * @return
     * @throws Exception
     *//*
	@SuppressWarnings("deprecation")
	public static String getStringDate(String dateString) throws Exception {

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = df.parse(dateString);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		return date.toLocaleString();

	}

	*//**
     * 将String日期类型转化为14位long类型数字，格式为 yyyyMMddHHmmss,例如2009-09-28
     * 15:30:25，则返回20090928153025。
     *
     *//*
	public static long getLongDate(String date) {
		String str = date;
		String str1 = str.replace("-", "").substring(0, 8);
		String str2= str.substring(str.length()-9,str.length()).replace(":", "").substring(0, 7);
		String str3=str2.substring(str2.length()-6,str2.length());
		str=str1+str3;
		long intDate = Long.parseLong(str);
		return intDate;
	}

	public static Date getDatebyString(String dateStr) throws ParseException {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = fmt.parse(dateStr);

		return date;

	}

	*//**
     * 返回yyyy-MM-dd-HHmmss格式的当前日期
     *
     * @return
     *//*
	public static String getCurrentTime() {
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	*//**
     * 将两个Date类型的日期进行比较，看date1 和 date2是否相等，返回boolean
     *
     * @param date1
     * @param date2
     * @return
     *//*
	public boolean isEquals(Date date1, Date date2) {
		boolean aa = false;
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.equals(cal2))
			aa = true;
		return aa;
	}

	*//**
     * 判断dateA小于等于dateB
     *
     * @param dateA
     * @param dateB
     * @return
     * @throws ParseException
     *//*
	public static boolean beforeAndEquals(String dateA, String dateB)
			throws ParseException {
		Date date1 = getDatebyString(dateA);
		Date date2 = getDatebyString(dateB);
		if (date1.after(date2)) {
			return false;
		}
		return true;
	}

	*//**
     * 判断dateA大于等于dateB
     *
     * @param dateA
     * @param dateB
     * @return
     *//*
	public boolean afterAndEquals(Date dateA, Date dateB) {
		if (dateA.before(dateB)) {
			return false;
		}
		return true;
	}*/

    /**
     * 获取当前时间，类型为long,
     * 时间单位：秒
     * @return
     */
    public static long getCurrentTimeToLong(){
        return (new Date().getTime())/1000;
    }

    /**
     * 将long类型的时间转换为String
     * 格式为：yyyy-MM-dd HH:mm:ss
     * @param time 时间单位：毫秒
     * @return
     */
    public static String getDateToString(long time ){
        return sdf.format(new Date(time));
    }

    /**
     * 将long类型的时间转换为String
     * 格式为：yyyy-MM-dd
     * @param time 时间单位：毫秒
     * @return
     */
    public static String getDateToString2(long time ){
        return sdf2.format(new Date(time));
    }

    /**
     * 将long类型的时间转换为String
     * 格式为：yyyy-MM-dd
     * @param time 时间单位：毫秒
     * @return
     */
    public static String getDateToString3(long time ){
        return sdf4.format(new Date(time));
    }

    public static String getDateToString4(Date date){return sdf4.format(date);};

    /**
     * 将秒转换为Date
     * @param time
     * @return Date
     */
    public static Date getDateBySecond(long time ){
        return new Date(time * 1000);
    }


    /**
     *  string类型转换为date类型
     *	strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
     *	HH时mm分ss秒，
     *	strTime的时间格式必须要与formatType的时间格式相同
     * @param strTime
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String strTime)
            throws ParseException {
        Date date = null;
        date = sdf.parse(strTime);
        return date;
    }


    /**
     *	获取第day天的开始时间
     * day=0表示当天，day=1表示前一天，day=2表示前二天，依次类推
     * @param day
     * @return 单位：秒
     * @throws ParseException
     */
    public static Long getStartTime(int day) throws ParseException{
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        Date time = (Date) currentDate.getTime().clone();
        long startTime = time.getTime() - (day * ONEDAY);
        String startTimeStr = getDateToString(startTime);
        return (stringToDate(startTimeStr).getTime())/1000;
    }

    /**
     *	获取第day天的结束时间
     * day=0表示当天，day=1表示前一天，day=2表示前二天，依次类推
     * @param day
     * @return 单位：秒
     */
    public static Long getEndTime(int day) throws ParseException{
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        Date time = (Date)currentDate.getTime().clone();
        long endTime = time.getTime() - (day * ONEDAY);
        String endTimeStr = getDateToString(endTime);
        return (stringToDate(endTimeStr).getTime())/1000;
    }

    /**
     * 获取month个月后的时间
     * @param date
     * @param month
     * @return (单位：秒)
     * @throws ParseException
     */
    public static long getAfterMonthsTime(Date date,int month) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return (calendar.getTime().getTime())/1000;
    }

    /**
     * 获取year年后的时间
     * @param date
     * @param year
     * @return(单位：秒)
     * @throws ParseException
     */
    public static long getAfterYearTime(Date date,int year) throws ParseException{
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return (calendar.getTime().getTime())/1000;
    }

    /**
     * 两个时间比较,time1是否大于time2
     * @param time1
     * @param time2(Integer 类型，时间单位：秒)
     * @return true or false
     */
    public static boolean compareTime(Integer time1,Integer time2){
        return time1 > time2;
    }

    /**
     * time时间与当前时间比较
     * @param time
     * @return 大于当前时间返回true，反之false
     */
    public static boolean compareCrrentTime(Integer time){
        return time > getCurrentTimeToLong();
    }

    /**
     *  time时间与当前时间加上addTime时间后比较
     * @param time
     * @param addTime
     * @return 大于返回true，反之false
     */
    public static boolean compareCrrentTime(Integer time,Long addTime){
        return time > getCurrentTimeToLong() + addTime;
    }

    /**
     * 两个时间比较
     * @param time1
     * @param time2
     * @return 如果 time1 > time2 返回 true，否则false
     * @throws ParseException
     */
    public static boolean compareDateTime(long time1,long time2) throws ParseException{
        return time1 > time2;
    }

    public static long getNowDatetime(){
        return Long.valueOf(sdf3.format(new Date()));
    }


    public static void main(String[] args) throws ParseException {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date = format.parse("2013-11-11 18:35:35");
        // System.out.println(format(date.getTime()));

    }


}
