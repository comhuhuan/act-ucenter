/**
 * @Title: SqlUtil.java 
 * @Package com.act.web.util 
 * @Description: sql语句工具类 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-11 上午10:50:58
 * @version V1.0   
 */
package com.act.ucenter.common.util;

public class SqlUtil {

	/**
	 * @Title: verifySQL
	 * @Description: 检验sql语句合法性
	 * @param sql
	 *            被检验sql
	 * @return boolean 合法返回true 非法返回false
	 * @create 2017-5-11 上午10:58:28
	 * @update 2017-5-11 上午10:58:28
	 */
	public static boolean verifySQL(String sql) {
		String countSql = "select count(*) from (" + sql + ") t";
		try {
			SpringUtil.getJdbcTemplate().queryForList(countSql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
