/**
 * @Title: CacheUtil.java 
 * @Package com.act.web.util 
 * @Description: 系统缓存类
 * @author   fmj
 * @modifier fmj
 * @date 2017-7-26 下午4:18:15
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.module.dnsm.service.EhCacheService;

public class EhCacheUtil {

	private static EhCacheService ehCacheService = SpringContextUtil
			.getBean("ehCacheServiceImpl");

	/**
	 * @Title: getGbtCode
	 * @Description: 得到区域下拉框 省市
	 * @create 2017-7-26 下午4:25:26
	 * @update 2017-7-26 下午4:25:26
	 */
	public static Object getGbtCode() {
		return ehCacheService.getGbtCode();
	}

	/**
	 * @Title: getGbtCode
	 * @Description: 得到区域下拉框 省市str
	 * @create 2017-7-26 下午4:25:26
	 * @update 2017-7-26 下午4:25:26
	 */
	public static Object getGbtStrCode() {
		return ehCacheService.getGbtStrCode();
	}

	/**
	 * @Title: getGbtAllCode
	 * @Description: 得到区域下拉框 省市县
	 * @create 2017-7-26 下午4:25:26
	 * @update 2017-7-26 下午4:25:26
	 */
	public static Object getGbtAllCode() {
		return ehCacheService.getGbtAllCode();
	}

	/**
	 * @Title: getIsoCode
	 * @Description: 得到国家代码下拉框 省市县
	 * @create 2017-7-26 下午4:25:26
	 * @update 2017-7-26 下午4:25:26
	 */
	public static Object getIsoCode() {
		return ehCacheService.getIsoCode();
	}

}
