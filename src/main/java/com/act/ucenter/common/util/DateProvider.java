package com.act.ucenter.common.util;

import java.util.Date;

/**
 * 日期提供者，使用它而不是直接取得系统时间，方便测试。
 * 
 * @author calvin
 */
public interface DateProvider {

	static final DateProvider DEFAULT = new CurrentDateProvider();

	Date getDate();

	java.sql.Date getSqlDate();

	java.sql.Timestamp getTimestamp();

	/**
	 * 返回当前的时间。
	 */
	public static class CurrentDateProvider implements DateProvider {

		
		public Date getDate() {
			return new Date();
		}
		public java.sql.Date getSqlDate() {
			return new java.sql.Date(new Date().getTime());
		}

		public java.sql.Timestamp getTimestamp(){
			return new java.sql.Timestamp(new Date().getTime());
		}
	}

	/**
	 * 返回设定的时间.
	 */
	public static class ConfigurableDateProvider implements DateProvider {

		private final Date date;

		public ConfigurableDateProvider(Date date) {
			this.date = date;
		}

		
		public Date getDate() {
			return date;
		}
		
		public java.sql.Date getSqlDate() {
			return new java.sql.Date(date.getTime());
		}

		public java.sql.Timestamp getTimestamp(){
			return new java.sql.Timestamp(date.getTime());
		}
	}

}
