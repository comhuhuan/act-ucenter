/**   
 * @Title: PropertyUtil.java 
 * @Package com.act.web.util 
 * @Description: (动态读取配置文件Util 类) 
 * @author   fmj
 * @date 2017-2-6 下午5:12:37 
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import org.apache.log4j.Logger;

import java.util.Hashtable;
import java.util.Properties;


public class PropertyUtil {

	private static Logger log = Logger.getLogger(PropertyUtil.class);

	private static Hashtable<String, Properties> pptContainer = new Hashtable<String, Properties>();
	
	/**
	 * 通过配置文件路径 和 属性  获得属性值 
	 */
	public final static String getValue(String propertyFilePath, String key) {
		Properties ppts = getProperties(propertyFilePath);
		return ppts == null ? null : ppts.getProperty(key);
	}
	
	/**
	 * 获得属性文件的Properties
	 */
	public final static Properties getProperties(String propertyFilePath) {
		if (propertyFilePath == null) {
			log.error("propertyFilePath is null!");
			return null;
		}
		Properties ppts = pptContainer.get(propertyFilePath);
		if (ppts == null) {
			ppts = loadPropertyFile(propertyFilePath);
			if (ppts != null) {
				pptContainer.put(propertyFilePath, ppts);
			}
		}
		return ppts;
	}
	
	/**
	 * 根据配置文件路径 加载属性
	 */
	private static Properties loadPropertyFile(String propertyFilePath) {
		java.io.InputStream is = PropertyUtil.class
				.getResourceAsStream(propertyFilePath);
		if (is == null) {
			return loadPropertyFileByFileSystem(propertyFilePath);
		}
		Properties ppts = new Properties();
		try {
			ppts.load(is);
			return ppts;
		} catch (Exception e) {
			log.debug("加载属性文件出错:" + propertyFilePath, e);
			return null;
		}
	}
	
	/**
	 * 从文件系统加载属性文件
	 */
	private static Properties loadPropertyFileByFileSystem(
			final String propertyFilePath) {
		try {
			Properties ppts = new Properties();
			ppts.load(new java.io.FileInputStream(propertyFilePath));
			return ppts;
		} catch (java.io.FileNotFoundException e) {
			log.error("FileInputStream(\"" + propertyFilePath
					+ "\")! FileNotFoundException: " + e);
			return null;
		} catch (java.io.IOException e) {
			log.error("Properties.load(InputStream)! IOException: " + e);
			return null;
		}
	}
}
