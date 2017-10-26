/**   
 * @Title: DateUtil.java
 * @Package com.act.web.util
 * @Description: 日期时间工具类
 * @author xujian
 * @modifier xujian
 * @date 2017-6-8 上午9:39:05
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	/**
	 * 周一
	 */
	private static final int MONDAY = 1;
	/**
	 * 数字1
	 */
	private static final int NUM_NOE = 1;

	/**
	 * 默认日期格式
	 */
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 
	 * @name 中文名称
	 * @Description: 获取指定日期前一天的时间
	 * @author xujian
	 * @date 创建时间:2017-6-8 上午9:42:45
	 * @param date
	 * @return
	 * @version V1.0
	 */
	public static Date getYesterday(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -NUM_NOE);
		return calendar.getTime();
	}

	/**
	 * 
	 * @name 中文名称
	 * @Description: 判断指定时间是不是周一
	 * @author xujian
	 * @date 创建时间:2017-6-8 上午9:56:57
	 * @param date
	 * @return 是周一返回true
	 * @version V1.0
	 */
	public static boolean checkTodayIsMonday(Date date) {
		Calendar calendar = Calendar.getInstance();
		if (null == date) {
			calendar.setTime(new Date());
		} else {
			calendar.setTime(date);
		}

		int week = calendar.get(Calendar.DAY_OF_WEEK) - NUM_NOE;
		if (week == MONDAY) {
			return true;
		}
		return false;
	}

	/**
	 * @Title: compareDate
	 * @Description: 日期小于或等于当前时刻时返回true
	 * @create 2017-7-25 下午3:32:47
	 * @update 2017-7-25 下午3:32:47
	 */
	public static boolean compareDate(String oldDate, String format) {
		if (format == null) {
			format = DEFAULT_DATE_FORMAT;
		}
		Date utilDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String nowDate = sdf.format(utilDate);
		int res = oldDate.compareTo(nowDate);
		if (res >= 0) {
			return false;
		} else {
			return true;
		}

	}
	
	public static String getCurrentTime(){
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		return sdf.format(new Date());
	}


}
