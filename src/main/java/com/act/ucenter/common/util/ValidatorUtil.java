/**   
 * @Title: ValidatorUtil.java 
 * @Package com.act.web.util 
 * @Description: (验证类) 
 * @author   fmj
 * @date 2017-2-17 下午4:14:18 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

	/**
	 * 
	 * @Title: checkEmpty
	 * @Description: 检查字符串是否为空，非空为true
	 * @create 2017-6-8 上午9:19:44
	 * @update 2017-6-8 上午9:19:44
	 */
	public static boolean validateStrEmpty(String str) {
		return (str != null && !"".equals(str.trim()) && !"null".equals(str
				.trim()));
	}
	
	/**
	 * 
	 * @Title: validateListEmpty
	 * @Description: 检查list是否为空，非空返回true
	 * @create 2017-6-19 上午11:15:13
	 * @update 2017-6-19 上午11:15:13
	 */
	public static boolean validateListEmpty(List list) {
		return list != null && list.size() > 0;
	}
	
	
	/**
	 * 
	 * @Title: validateMobilePhone
	 * @Description:校验移动电话
	 * @create 2017-6-8 上午9:12:07
	 * @update 2017-6-8 上午9:12:07
	 */
	public static boolean validateMobilePhone(String value) {
		String reg = "(1\\d{10})";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(value);
		return m.matches();
	}

	/**
	 * 
	 * @Title: validatePhone
	 * @Description: 校验固定电话
	 * @create 2017-6-8 上午9:12:23
	 * @update 2017-6-8 上午9:12:23
	 */
	public static boolean validatePhone(String value) {
		Pattern p = Pattern
				.compile("^(([0\\+]\\d{2,3}-)?(\\d{2,4})-)(\\d{7,8})(-(\\d{3,}))?$");
		Matcher m = p.matcher(value);
		boolean b = m.matches();
		return b;
	}

	/**
	 * 
	 * @Title: validateEmail
	 * @Description: 校验电子邮箱
	 * @create 2017-6-8 上午9:12:40
	 * @update 2017-6-8 上午9:12:40
	 */
	public static boolean validateEmail(final String email) {
		if (email != null && !"".equals(email)) {
			int n = email.indexOf("@");// 获取@位置
			int m = email.indexOf(".", n);// 判断@后面是否有点
			int k = email.indexOf("..");// 判断是否存在多个点连一起
			if (n != -1 && m != -1 && k == -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Title: validateDate
	 * @Description: 检验日期格式是否正确
	 * @param value
	 *            校验值
	 * @param dateFormat
	 *            日期格式 如:yyyyMMdd/yyyy-MM-dd
	 * @create 2017-6-8 上午9:30:53
	 * @update 2017-6-8 上午9:30:53
	 */
	public static boolean validateDate(String value, String dateFormat) {
		try {
			Format f = new SimpleDateFormat(dateFormat);
			Date d = (Date) f.parseObject(value);
			String tmp = f.format(d);
			return tmp.equals(value);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @Title: validateIDCard
	 * @Description:校验身份证
	 * @create 2017-6-8 上午9:13:27
	 * @update 2017-6-8 上午9:13:27
	 */
	public static boolean validateIDCard(String value) {
		boolean tipInfo = false;// 记录错误信息
		String Ai = "";
		// 判断号码的长度 15位或18位
		if (value.length() != 15 && value.length() != 18) {
			return tipInfo;
		}

		// 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
		if (value.length() == 18) {
			Ai = value.substring(0, 17);
		} else if (value.length() == 15) {
			Ai = value.substring(0, 6) + "19" + value.substring(6, 15);
		}
		if (validateNum(Ai) == false) {
			return tipInfo;
		}

		// 判断出生年月是否有效
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 日期
		if (validateIdDate(strYear + "-" + strMonth + "-" + strDay) == false) {
			return tipInfo;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
							strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				return tipInfo;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			return tipInfo;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			return tipInfo;
		}

		// 判断地区码是否有效
		Hashtable<String, String> areacode = GetAreaCode();
		// 如果身份证前两位的地区码不在Hashtable，则地区码有误
		if (areacode.get(Ai.substring(0, 2)) == null) {
			return tipInfo;
		}

		if (isVarifyCode(Ai, value) == false) {
			return tipInfo;
		}
		tipInfo = true;

		return tipInfo;
	}

	/**
	 * 
	 * @Title: validateNum
	 * @Description: 判断字符串是否为数字
	 * @create 2017-6-8 上午9:18:07
	 * @update 2017-6-8 上午9:18:07
	 */
	public static boolean validateNum(String strnum) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(strnum);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @Title: validateDate
	 * @Description:判断字符串出生日期是否符合正则表达式
	 * @create 2017-6-8 上午9:18:40
	 * @update 2017-6-8 上午9:18:40
	 */
	public static boolean validateIdDate(String strDate) {

		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/*
	 * 判断第18位校验码是否正确 第18位校验码的计算方式： 　　1. 对前17位数字本体码加权求和 　　公式为：S = Sum(Ai * Wi), i
	 * = 0, ... , 16 　　其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5
	 * 8 4 2 1 6 3 7 9 10 5 8 4 2 　　2. 用11对计算结果取模 　　Y = mod(S, 11) 　　3.
	 * 根据模的值得到对应的校验码 　　对应关系为： 　　 Y值： 0 1 2 3 4 5 6 7 8 9 10 　　校验码： 1 0 X 9 8 7 6
	 * 5 4 3 2
	 */
	private static boolean isVarifyCode(String Ai, String IDStr) {
		String[] VarifyCode = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		int sum = 0;
		for (int i = 0; i < 17; i++) {
			sum = sum + Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = sum % 11;
		String strVerifyCode = VarifyCode[modValue];
		Ai = Ai + strVerifyCode;
		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				return false;

			}
		}
		return true;
	}

}
