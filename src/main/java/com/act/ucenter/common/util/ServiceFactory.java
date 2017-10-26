package com.act.ucenter.common.util;


public class ServiceFactory {
	public static <T>T getService(Class<T> t){
		return SpringUtil.getApplicationContext().getBean(t);
	}
}
