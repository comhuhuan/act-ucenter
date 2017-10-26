package com.act.ucenter.common.util;

import com.kpr.kui.definition.Tab;

public class SystemConfig {
	//是否启用用户自动锁定
	public static boolean isUserAutoLock() {
		return ConfigUtil.getBoolean("sys.isUserAutoLock", true);
	}
	
	//自动锁定时间（秒）
	public static int getUserLockTime() {
		return ConfigUtil.getInt("sys.userLockTime", 5*60);
	}

	//登录尝试次数
	public static int getUserLoginRetryCount() {
		return ConfigUtil.getInt("sys.userLoginRetryCount", 5);
	}
	
	/**
	 * 是否启用登录验证码功能
	 * @return
	 */
	public static boolean isUseCaptcha() {
		return ConfigUtil.getBoolean("sys.isUseCaptcha",false);
	}
	
	/**
	 * 是否启用账套菜单权限控制功能
	 * @return
	 */
	public static boolean isUseAccountAcl() {
		return ConfigUtil.getBoolean("sys.isUseAccountAcl",false);
	}
	
	/**
	 * 是否默认启用记录审计功能
	 * @return
	 */
	
	public static boolean isAudit() {
		return ConfigUtil.getBoolean("sys.isAudit", false);
	}
	
	//默认数据访问范围
	public static String getDefaultDataAccessLevel() {
		return ConfigUtil.getString("sys.defaultDataAccessLevel",Tab.DataAccessLevel_Current);
	}
}
