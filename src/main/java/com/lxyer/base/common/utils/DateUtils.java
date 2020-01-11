package com.lxyer.base.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * ClassName:	DateUtils <br/>
 * Description:时间操作工具类
 * <br/>
 * Date:		2015年7月28日 下午4:12:01 <br/>
 * <p/>
 * Modification History:<br/>
 * Date           Author          Version          Description<br/>
 * -----------------------------------------------------------------------------------<br/>
 * 2019年9月28日      wangjinlong        1.0              <初始创建><br/>
 *
 * @see
 */
public class DateUtils {

    private static final String[] UNIT_DESC = new String[]{"天", "小时", "分钟", "秒"};

    /**
     * 获得当前系统时间，格式为yyyyMMdd
     *
     * @return 格式化后的时间
     */
    public static String currentYYYYMMDD() {
        return getStrByDate(new Date(), "yyyyMMdd");
    }

    /**
     * 获得当前系统时间，格式为HHmmss
     *
     * @return 格式化后的时间
     */
    public static String currentHHMMSS() {
        return getStrByDate(new Date(), "HHmmss");
    }

    /**
     * 获得当前系统时间，格式为yyyy年MM月dd日
     *
     * @return 格式化后的时间
     */
    public static String getcurrentYYYYMMDD() {
        return getStrByDate(new Date(), "yyyy年MM月dd日");
    }
    
    /**
     * 获得当前系统时间，格式为yyyyMMddHHmmss
     *
     * @return 格式化后的时间
     */
    public static String currentYYYYMMDDHHmmss() {
        return getStrByDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 根据给定的字符串如：yyyy-MM-dd HH:mm:ss，（必须是这种格式） 返回一个日期日期形式
     *
     * @param strDate 要抛析的字符串,且字符串的形式必须：2007-09-10 07:00:00
     * @return 将字符串抛析成日期的格式返回
     * @throws ParseException 解析 format 字段失败
     */
    public static Date getDateByStr(String strDate, String format) throws ParseException {
        assert strDate != null && format != null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        if(strDate != null && !"null".equals(strDate)) {
        	date = simpleDateFormat.parse(strDate);
        }
        return date;
    }

    /**
     * 根据给定的日期，返回给定的字符串， 返回 字符串的形式是：yyyy-MM-dd HH:mm:ss
     *
     * @param date 要格式化的日期
     * @return 将日期格式化后返回的字符串，以这中格式返回：yyyy-MM-dd HH:mm:ss
     */
    public static String getStrByDate(Date date, String format) {
        assert date != null && format != null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 得到当前时间
     *
     * @return 当前时间
     */
    public static Date getDayOfMonth() {
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    /**
     * 得到每月第一天
     *
     * @param date 日期
     * @return 日期月份的第一天
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar nowday = Calendar.getInstance();
        nowday.setTime(date);
        nowday.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        return nowday.getTime();
    }

    /**
     * 得到每月最后一天
     *
     * @param date 日期
     * @return 日期月份最后一天
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar nowday = Calendar.getInstance();
        nowday.setTime(date);
        nowday.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        nowday.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return nowday.getTime();
    }

    /**
     * 获取当前年份 格式：yyyy
     *
     * @param date 当前时间
     * @return year
     */
    public static String getCurrYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date currYear = calendar.getTime();
        return String.valueOf(dateFormat.format(currYear));
    }

    /**
     * 获取当前月份 格式：MM
     *
     * @param date 当前时间
     * @return Date
     */
    public static String getCurrMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        Date currMonth = calendar.getTime();
        return String.valueOf(dateFormat.format(currMonth));
    }

    /**
     * 获取当前月份 格式：MM
     *
     * @param date 当前时间
     * @return Date
     */
    public static String getCurrDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        Date currMonth = calendar.getTime();
        return String.valueOf(dateFormat.format(currMonth));
    }

    /**
     * 得到此日期的最后一天
     *
     * @param d 日期
     * @return 最后一天
     */
    public static Date getLastDayByDate(Date d) {
        Calendar newday = Calendar.getInstance();
        newday.setTime(d);
        int lastday;
        int month = newday.get(Calendar.MONTH);
        do {
            lastday = newday.get(Calendar.DAY_OF_MONTH);
            newday.add(Calendar.DAY_OF_MONTH, 1);
        } while (newday.get(Calendar.MONTH) == month);
        newday.set(Calendar.MONTH, month);

        newday.set(Calendar.DAY_OF_MONTH, lastday);
        return newday.getTime();
    }

    /**
     * 将 yyyyMMdd 的字符窜 转化成 yyyy-MM-dd
     *
     * @param dateString yyyyMMdd格式的日期
     * @return yyyy-MM-dd格式的日期
     * @throws ParseException
     */
    public static String formatyyyyMMdd(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = simpleDateFormat.parse(dateString);
        SimpleDateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd");
        return formatStr.format(date);
    }

    /**
     * 将 yyyyMMdd 的字符窜 转化成 yyyy-MM-dd HH:mm:ss
     *
     * @param dateString
     * @return
     * @throws ParseException
     */
    public static String formatyyyyMMddHHmmss(String dateString) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = simpleDateFormat.parse(dateString);
        SimpleDateFormat formatStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatStr.format(date);
    }

    /**
     * 获取当前年份 格式：yyyy
     *
     * @return Date
     */
    public static int getCurrYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date currYearFirst = calendar.getTime();
        return Integer.valueOf(dateFormat.format(currYearFirst));
    }

    /**
     * 获取当前时间前三月
     *
     * @return Date
     */
    public static Date getLastThreeMonths() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -3);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间前一个月
     *
     * @return Date
     */
    public static Date getLastOneMonths() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -1);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取当前时间前六个月
     *
     * @return Date
     */
    public static Date getLastSixMonths() {
        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.MONTH, -6);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取某年第一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearFirst(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 获取某年最后一天日期
     *
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearLast(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 格式化时间
     *
     * @param date   时间
     * @param format 格式化模板
     * @return 格式化后的时间
     */
    public static String date2Str(Date date, String format) {
        return getStrByDate(date, format);
    }

    /**
     * 格式化持续时间<br/>
     * 将持续时间，格式化为 xx天xx小时xx分钟xx秒 如果 "xx" 为0 自动缺省。
     *
     * @param seconds 持续时间,单位(秒)
     * @return 格式化后的字符串
     * @see TimeUnit 时间单位转换工具
     * @since 1.5
     */
    public static String convertSeconds2Str(long seconds) {
        StringBuilder sb = new StringBuilder();
        long[] date = {TimeUnit.SECONDS.toDays(seconds), TimeUnit.SECONDS.toHours(seconds) % 24,
                TimeUnit.SECONDS.toMinutes(seconds) % 60, TimeUnit.SECONDS.toSeconds(seconds) % 60};
        for (int i = 0; i < date.length; i++) {
            long l = date[i];
            if (l > 0) {
                sb.append(l).append(UNIT_DESC[i]);
            }
        }
        return sb.toString();
    }


    /**
     * 通过日历类的Calendar.add方法第二个参数-1达到前一天日期的效果
     *
     * @return
     */
    public static String getYesterdayByCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date time = calendar.getTime();
        return getStrByDate(time, "yyyyMMdd");
    }


    /**
     * 获取今天一点整
     *
     * @return
     */
    public static Date todayOneoclock() throws ParseException {
        String yyyyMMdd = getStrByDate(new Date(), "yyyy-MM-dd");
        String time = yyyyMMdd + " 01:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(time);
        return date;
    }


    /***
     * <b>function:</b> 将数字转化为大写
     * @createDate 2010-5-27 上午10:28:12
     * @param num 数字
     * @return 转换后的大写数字
     */
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        //String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String u[] = {"○", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        char[] str = String.valueOf(num).toCharArray();
        String rstr = "";
        for (int i = 0; i < str.length; i++) {
            rstr = rstr + u[Integer.parseInt(str[i] + "")];
        }
        return rstr;
    }

    /***
     * <b>function:</b> 月转化为大写
     * @createDate 2010-5-27 上午10:41:42
     * @param month 月份
     * @return 返回转换后大写月份
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    /***
     * <b>function:</b> 日转化为大写
     * @createDate 2010-5-27 上午10:43:32
     * @param day 日期
     * @return 转换大写的日期格式
     */
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    // 日期转化为大小写
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }
}
