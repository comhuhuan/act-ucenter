package com.act.ucenter.common.util;

public class ConvertUtil {
//	public static String DATE_FORMAT = "yyyy-MM-dd"; 
//	public static String LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"; 
//	public static java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
//	
//	@SuppressWarnings("unchecked")
////	//将值转换为指定类型对象
////	private Object convert(Object obj, Class<?> type){
////		Object value;
////		if (type.isPrimitive()){
////			if (type==boolean.class){
////				if (obj==null)
////					value = false;
////				else if (obj instanceof Boolean){
////					value = ((Boolean)obj).booleanValue();
////				}
////				else
////					value = Boolean.valueOf(obj.toString());
////			}
////			else if (type==int.class){
////				if (obj==null)
////					value = 0;
////				else if (obj instanceof Integer){
////					value = ((Integer)obj).intValue();
////				}
////				else
////					value = Integer.valueOf(obj.toString());
////			}
////			else if (type==byte.class){
////				if (obj==null)
////					value = (byte)0;
////				else if (obj instanceof Byte){
////					value = ((Byte)obj).byteValue();
////				}
////				else
////					value = Byte.valueOf(obj.toString());
////			}
////			else if (type==short.class){
////				if (obj==null)
////					value = (short)0;
////				else if (obj instanceof Short){
////					value = ((Short)obj).shortValue();
////				}
////				else
////					value = Short.valueOf(obj.toString());
////			}
////			else if (type==long.class){
////				if (obj==null)
////					value = 0L;
////				else if (obj instanceof Long){
////					value = ((Long)obj).longValue();
////				}
////				else
////					value = Long.valueOf(obj.toString());
////			}
////			else if (type==double.class){
////				if (obj==null)
////					value = (double)0;
////				else if (obj instanceof Double){
////					value = ((Double)obj).doubleValue();
////				}
////				else
////					value = Double.valueOf(obj.toString());
////			}
////			else if (type==float.class){
////				if (obj==null)
////					value = (float)0;
////				else if (obj instanceof Float){
////					value = ((Float)obj).floatValue();
////				}
////				else
////					value = Float.valueOf(obj.toString());
////			}
////			else
////				throw new RuntimeException("Unknown primitive type: " + type.getName());
////			
////		}
////		else if (obj==null){
////			value = null;
////		}
////		else {
////			if (type==Boolean.class){
////				if (obj instanceof Boolean){
////					value = ((Boolean)obj);
////				}
////				else
////					value = Boolean.valueOf(obj.toString());
////			}
////			else if (type==Integer.class){
////				if (obj instanceof Integer){
////					value = ((Integer)obj);
////				}
////				else
////					value = Integer.valueOf(obj.toString());
////			}
////			else if (type==Byte.class){
////				if (obj instanceof Byte){
////					value = ((Byte)obj);
////				}
////				else
////					value = Byte.valueOf(obj.toString());
////			}
////			else if (type==Short.class){
////				if (obj instanceof Short){
////					value = ((Short)obj);
////				}
////				else
////					value = Short.valueOf(obj.toString());
////			}
////			else if (type==Long.class){
////				if (obj instanceof Long){
////					value = (Long)obj;
////				}
////				else
////					value = Long.valueOf(obj.toString());
////			}
////			else if (type==Double.class){
////				if (obj instanceof Double){
////					value = (Double)obj;
////				}
////				else
////					value = Double.valueOf(obj.toString());
////			}
////			else if (type==Float.class){
////				if (obj instanceof Float){
////					value = (Float)obj;
////				}
////				else
////					value = Float.valueOf(obj.toString());
////			}
////			else if (type==BigDecimal.class){
////				if (obj instanceof BigDecimal){
////					value = (BigDecimal)obj;
////				}
////				else
////					value = new BigDecimal(obj.toString());
////			}
////			else
////				throw new RuntimeException("Unknown primitive type: " + type.getName());			
////		}
////		return value;
////	}
//	
//	public static Object createDefaultValue(Class<?> clazz) {
//		if (clazz == String.class)
//			return "";
//		else if (clazz == Integer.class || clazz == int.class)    //  note that Integer is stored as BD
//			return 0;
//		else if (clazz == Long.class|| clazz == long.class)    //  note that Integer is stored as BD
//			return 0L;
//		else if (clazz == Byte.class|| clazz == byte.class)    
//			return (byte)0;
//		else if (clazz == Short.class || clazz == short.class)    
//			return (short)0;
//		else if (clazz == Float.class || clazz == float.class)    
//			return (float)0;
//		else if (clazz == Double.class || clazz == double.class)    
//			return (double)0;
//		else if (clazz == BigDecimal.class )    
//			return BigDecimal.ZERO;
//		else if (clazz == Boolean.class)
//			return false;
//		else
//			return null;
//	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	public static <T> T toObject(String str,Class<T> clazz,String dateFormat,boolean useDefault) {
//		Object defaultValue = useDefault?createDefaultValue(clazz):null;
//		if (clazz == String.class)
//			return (T)str;
//		else if (clazz == Integer.class || clazz == int.class)   
//			return (T)ConvertUtil.toInt(str,(Integer)defaultValue);
//		else if (clazz == Long.class|| clazz == long.class)    
//			return (T)ConvertUtil.toLong(str,(Long)defaultValue);
//		else if (clazz == Byte.class|| clazz == byte.class)    
//			return (T)ConvertUtil.toByte(str,(Byte)defaultValue);
//		else if (clazz == Short.class || clazz == short.class)    
//			return (T)ConvertUtil.toShort(str,(Short)defaultValue);
//		else if (clazz == Float.class || clazz == float.class)    
//			return (T)ConvertUtil.toFloat(str,(Float)defaultValue);
//		else if (clazz == Double.class || clazz == double.class)    
//			return (T)ConvertUtil.toDouble(str,(Double)defaultValue);
//		else if (clazz == BigDecimal.class )    
//			return (T)ConvertUtil.toBigDecimal(str,(BigDecimal)defaultValue);
//		else if (clazz == java.util.Date.class)    
//			return (T)ConvertUtil.toDate(str,dateFormat);
//		else if (clazz == java.sql.Date.class)    
//			return (T)ConvertUtil.toSqlDate(str,dateFormat);
//		else if (clazz == java.sql.Timestamp.class)    
//			return (T)ConvertUtil.toTimestamp(str,dateFormat);
//		else if (clazz == Boolean.class)
//			return (T)ConvertUtil.toBoolean(str,null);
//		else if (clazz == byte[].class)
//			try {
//				return (T)str.getBytes("UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				throw new RuntimeException(e);
//			}
//		else {
//			Constructor[] clist = clazz.getConstructors();
//			for (Constructor c : clist){
//				if (c.getParameterTypes()!=null&&c.getParameterTypes().length==1 && c.getParameterTypes()[0]==String.class){
//					try {
//						return (T) c.newInstance(str);
//					}catch (RuntimeException re){
//						throw re;
//					} catch (Exception e) {
//						throw new RuntimeException(e);
//					}
//				}
//			}
//			throw new IllegalArgumentException("'类型不支持：" + clazz.getName());
//			
//		}
//	}
//	
//	public static String toStr(Object obj){
//		if (obj==null)
//			return null;
//		else if (obj.getClass() == java.util.Date.class)    
//			return DateUtil.toString((java.util.Date)obj,DATE_FORMAT);
//		else if (obj.getClass() == java.sql.Date.class)    
//			return DateUtil.toString((java.sql.Date)obj,DATE_FORMAT);
//		else if (obj.getClass()== java.sql.Timestamp.class)    
//			return DateUtil.toString((java.sql.Timestamp)obj,LONG_DATE_FORMAT);
//		else if (obj.getClass()==Boolean.class){
//			return ((Boolean)obj).booleanValue()?"true":"false";
//		}
//		else if (obj.getClass() == byte[].class)
//			try {
//				return new String((byte[])obj,"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				throw new RuntimeException(e);
//			}
//		else
//			return obj.toString();
//	}
//	
////	public static Byte toByte(String s) {
////		return toByte(s, null);
////	}
////	
//	public static Byte toByte(String s,Byte defaultValue) {
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return Byte.parseByte(s);
//	}
//
////	public static Short toShort(String s) {
////		return toShort(s,null);
////	}
//	
//	public static Short toShort(String s, Short defaultValue) {
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return Short.parseShort(s);
//	}
//	
//	public static Integer toInt(String s,Integer defaultValue) {
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return Integer.parseInt(s);
//	}
//	
//	public static Long toLong(String s,Long defaultValue){
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return Long.parseLong(s);
//	}
//
//	public static Float toFloat(String s,Float defaultValue) {
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return Float.parseFloat(s);
//	}
//
//	public static Double toDouble(String s, Double defaultValue) {
//		if (s == null || s.trim().length() == 0)
//			return defaultValue;
//		return Double.parseDouble(s.trim());
//	}
//
//	public static BigDecimal toBigDecimal(String s, BigDecimal defaultValue){
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return new BigDecimal(s);
//	}
//	
//
//	public static Boolean toBoolean(String s, Boolean defaultValue) {
//		if (s==null||s.length()==0)
//			return defaultValue;
//		return "Y".equalsIgnoreCase(s)||"true".equalsIgnoreCase(s)||"on".equalsIgnoreCase(s);
//	}
//
////	public static Byte toByte(String s,String fieldName) {
////		try {
////			return toByte(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static Short toShort(String s,String fieldName) {
////		try {
////			return toShort(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////	
////	public static Integer toInt(String s,String fieldName) {
////		try {
////			return toInt(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////	
////	public static Long toLong(String s,String fieldName){
////		try {
////			return toLong(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static Float toFloat(String s,String fieldName) {
////		try {
////			return toFloat(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static Double toDouble(String s,String fieldName) {
////		try {
////			return toDouble(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static BigDecimal toBigDecimal(String s,String fieldName){
////		try {
////			return toBigDecimal(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////	
//////	public static java.util.Date toDate(String s) {
//////		return toDate(s, DATE_FORMAT);
//////	}
//////	
//////	public static java.sql.Date toSqlDate(String s) {
//////		return toSqlDate(s, DATE_FORMAT);
//////	}
////
//	public static java.sql.Date toSqlDate(String s,String dateFormat) {
//		if (s==null||s.trim().length()==0)
//			return null;
//		java.util.Date d = DateUtil.toDate(s, dateFormat);
//		if (d!=null)
//			return new java.sql.Date(d.getTime());
//		else
//			return null;
//	}
//	public static java.util.Date toDate(String s,String dateFormat) {
//		return DateUtil.toDate(s, dateFormat);
//	}
////	
////	public static java.util.Date toDate(String s,String dateFormat,String fieldName) {
////		try {
////			return toDate(s,dateFormat);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static java.sql.Date toSqlDate(String s,String dateFormat,String fieldName) {
////		try {
////			return toSqlDate(s,dateFormat);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
//	public static Timestamp toTimestamp(String s,String dateFormat) {
//		if (s==null||s.trim().length()==0)
//			return null;
//		s = StringUtils.replace(s, "T", " ");
//		java.util.Date d = DateUtil.toDate(s, dateFormat);
//		if (d==null)
//			d = DateUtil.toDate(s, DATE_FORMAT);
//		return DateUtil.toTimestamp(d);
//	}
////
////
////	public static Timestamp toTimestamp(String s,String dateFormat,String fieldName) {
////		try {
////			return toTimestamp(s,dateFormat);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
////
////	public static Boolean toBoolean(String s,String fieldName) {
////		try {
////			return toBoolean(s);
////		}catch (Exception e){
////			throw new RuntimeException("字段'" + fieldName+"'转换错误：" + s);
////		}
////	}
}
