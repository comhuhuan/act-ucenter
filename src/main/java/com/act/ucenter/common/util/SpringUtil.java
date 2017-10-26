package com.act.ucenter.common.util;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Lazy(false)
public class SpringUtil implements ApplicationContextAware,ServletContextAware,InitializingBean {
	private static ApplicationContext ctx;
	private static JdbcTemplate jdbcTemplate = null;
	private static NamedParameterJdbcTemplate namedParameterJdbcTemplate = null;
	private static ServletContext servletContext;
	private static SecurityManager securityManager;
	//缓存赖关系，key为被依赖的缓存名称，value为依赖的缓存名称列表
	private static Map<String,List<String>> cacheDependency = new HashMap<String,List<String>>();
	private static CacheManager cacheManager;
	
	public SpringUtil() {

	}
	
	/*
	 * 清除依赖的缓存
	 */
	public static void clearDependentCaches(String cacheName){
		List<String> dependents = cacheDependency.get(cacheName);
		if (dependents!=null){
			for (String dcachename : dependents){
				Cache dcache = getCache(dcachename);
				if (dcache!=null)
					dcache.clear();
				clearDependentCaches(dcachename);
			}
		}
	}
	
	public final static Cache getCache(String cacheName,String[] dependendCaches){
		if (dependendCaches!=null){
			for (String dcacheName : dependendCaches){
				List<String> dependents = cacheDependency.get(dcacheName);
				if (dependents==null){
					dependents = new ArrayList<String>();
					cacheDependency.put(dcacheName, dependents);
				}
				if (dependents.indexOf(cacheName)<0)
					dependents.add(cacheName);
			}
		}
		return getCacheManager().getCache(cacheName);
	}

	public final static Cache getCache(String cacheName){
		return getCacheManager().getCache(cacheName);
	}

	public final static CacheManager getCacheManager(){
		return cacheManager;
	}
	
	public void setCacheManager(CacheManager cacheManager) {
		SpringUtil.cacheManager = cacheManager;
	}

	public final static ApplicationContext getApplicationContext(){
		return ctx;
	}
	
	public void setApplicationContext(ApplicationContext aCtx) throws BeansException {
		ctx = aCtx;
	}

	public final static JdbcTemplate getJdbcTemplate() {
//		if (jdbcTemplate==null){
//			jdbcTemplate = ctx.getBean(JdbcTemplate.class);
//		}
		return jdbcTemplate;
	}
	
	public void setJdbcTemplate(JdbcTemplate aJdbcTemplate){
		jdbcTemplate = aJdbcTemplate;
	}
	
	public final static NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
//		if (namedParameterJdbcTemplate==null){
//			namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
//		}
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate aNamedParameterJdbcTemplate){
		namedParameterJdbcTemplate = aNamedParameterJdbcTemplate;
	}
	
	public final static ServletContext getServletContext() {
		return servletContext;
	}
	
	public void setServletContext(ServletContext aServletContext) {
		servletContext = aServletContext;
	}
	
	public static PlatformTransactionManager getTransactionManager() {
		return (PlatformTransactionManager)ctx.getBean("transactionManager");
	}
	
	public static TransactionTemplate getTransactionTemplate() {
		return (TransactionTemplate)ctx.getBean("transactionTemplate");
	}

	public static SecurityManager getSecurityManager() {
		return securityManager;
	}

	public void setSecurityManager(SecurityManager aSecurityManager) {
		securityManager = aSecurityManager;
		//为后台任务程序初始化Shiro
		SecurityUtils.setSecurityManager(securityManager);
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		UpgradeManager.upgrade();
	}

//	@Autowired
//	private static SqlSession sqlSession;
//	public final static SqlSession getSqlSession() {
//		return sqlSession;
//	}
//
//	public void setSqlSession(SqlSession sqlSession) {
//		SpringUtil.sqlSession = sqlSession;
//	}
	
//	/**
//	 * 获取国际化消息
//	 * 
//	 * @param code
//	 *            代码
//	 * @param args
//	 *            参数
//	 * @return 国际化消息
//	 */
//	public static String getMessage(String code, Object... args) {
//		LocaleResolver localeResolver = ctx.getBean("localeResolver", LocaleResolver.class);
//		Locale locale = localeResolver.resolveLocale(null);
//		return ctx.getMessage(code, args, locale);
//	}
	
}
