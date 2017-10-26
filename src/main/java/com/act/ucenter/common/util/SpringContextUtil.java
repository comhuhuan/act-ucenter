/**   
 * @Title: SpringContextUtil.java 
 * @Package com.act.web.util 
 * @Description:  静态变量保存Spring ApplicationContext,SpringContextUtil.getBean("")取出
 * @author   fmj
 * @date 2017-3-20 下午4:05:52 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtil implements ApplicationContextAware,
		DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Log logger = LogFactory.getLog(SpringContextUtil.class);

	/**
	 *
	* @Title: getApplicationContext
	* @Description: 取得存储在静态变量中的ApplicationContext
	* @param @return    设定文件
	* @return ApplicationContext    返回类型
	* @throws
	 */
	public static ApplicationContext getApplicationContext() {
		assertContextInjected();
		return applicationContext;
	}
	
	/*
	 * (非 Javadoc) <p>Title: setApplicationContext</p> <p>Description:
	 * 实现ApplicationContextAware接口, 注入Context到静态变量中</p>
	 *
	 * @param arg0
	 *
	 * @throws BeansException
	 *
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		logger.debug("注入ApplicationContext到SpringContextHolder:"
				+ applicationContext);

		if (SpringContextUtil.applicationContext != null) {
			logger.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:"
					+ SpringContextUtil.applicationContext);
		}

		SpringContextUtil.applicationContext = applicationContext; // NOSONAR
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		assertContextInjected();
		return (T) applicationContext.getBean(name);
	}
	
	/**
	 * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
	 */
	public static <T> T getBean(Class<T> requiredType) {
		assertContextInjected();
		return applicationContext.getBean(requiredType);
	}

	/**
	 *
	 * @Title: clear
	 * @Description: 清除SpringContextHolder中的ApplicationContext为Null
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private static void clear() {
		logger.debug("清除SpringContextHolder中的Apphttp://localhost:8081/licationContext:"
				+ applicationContext);
		applicationContext = null;
	}

	/**
	 *
	 * @Title: assertContextInjected
	 * @Description: 检查ApplicationContext不为空
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private static void assertContextInjected() {
		if (applicationContext == null) {
			throw new IllegalStateException(
					"applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

	/*
	 * (非 Javadoc) <p>Title: destroy</p> <p>Description: </p>
	 *
	 * @throws Exception
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		clear();
	}

}
