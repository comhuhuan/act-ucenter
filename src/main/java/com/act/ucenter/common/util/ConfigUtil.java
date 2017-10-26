package com.act.ucenter.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

/**
 * 配置工具
 * @author James Zhao
 *
 */
public class ConfigUtil {
	//属性文件的路径   
    static String profilepath="/config.properties";
    
    /**  
    * 采用静态方法  
    */   
    private static Properties properties = new Properties();   
    static {   
        try {
        	Resource resource = new ClassPathResource(profilepath);
        	properties = PropertiesLoaderUtils.loadProperties(resource);
        } catch (FileNotFoundException e) {   
            e.printStackTrace();   
            throw new RuntimeException(e);   
        } catch (IOException e) {          
            throw new RuntimeException(e);   
        }   
    }   
  
    /**
     * 字符串值  
     * @param key
     * @return
     */
    public static String getString(String key) {   
        return properties.getProperty(key);   
    }
    
    public static String getString(String key,String defaultValue) {   
    	String value=properties.getProperty(key);
    	if (value!=null)
    		return value;
    	else
    		return defaultValue;
    }
    /**
     * 整形值
     * @param key
     * @return
     */
    public static Integer getInt(String key) {  
    	String value=properties.getProperty(key);
    	if (value!=null&&value.trim().length()>0)
    		return Integer.parseInt(value);
    	else
    		return null;
    }
    
    public static int getInt(String key, int defaultValue) {
    	Integer value = getInt(key);
    	if (value==null)
    		return defaultValue;
    	else
    		return value;
    }
    
    /**
     * 长整形值
     * @param key
     * @return
     */
    public static Long getLong(String key) {   
    	String value=properties.getProperty(key);
    	if (value!=null&&value.trim().length()>0)
    		return Long.parseLong(value);
    	else
    		return null;
    }
    
    public static long getLong(String key, long defaultValue) {
    	Long value = getLong(key);
    	if (value==null)
    		return defaultValue;
    	else
    		return value;
    }
    
    /**
     * 双精度值
     * @param key
     * @return
     */
    public static Double getDouble(String key) {   
    	String value=properties.getProperty(key);
    	if (value!=null&&value.trim().length()>0)
    		return Double.parseDouble(value);
    	else
    		return null;
    }
    
    public static double getDouble(String key, double defaultValue) {
    	Double value = getDouble(key);
    	if (value==null)
    		return defaultValue;
    	else
    		return value;
    }

    public static BigDecimal getBigDecimalValue(String key) {
    	String value = properties.getProperty(key);
    	if (value!=null&&value.trim().length()>0)
    		return new BigDecimal(value);
    	else
    		return null;
    }
    
    public static boolean getBoolean(String key) {
    	return getBoolean(key,false);
    }
    
    public static boolean getBoolean(String key, boolean defaultValue) {
    	String value=properties.getProperty(key);
    	if (value!=null&&value.trim().length()>0)
    		return "true".equalsIgnoreCase(value)||"y".equalsIgnoreCase(value)||"1".equalsIgnoreCase(value);
    	else
    		return defaultValue;
    }
    
}
