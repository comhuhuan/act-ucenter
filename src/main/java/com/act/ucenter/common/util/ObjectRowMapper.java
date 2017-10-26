package com.act.ucenter.common.util;

import com.act.framework.entity.BaseEntity;
import javassist.Modifier;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ObjectRowMapper<T> implements org.springframework.jdbc.core.RowMapper<T>{
	private Class<T> requiredType;
	private Map<String,PropertyDescriptor> propertyMap = null;
	public ObjectRowMapper(Class<T> requiredType){
		this.requiredType = requiredType;
	}
	

	/**
	 * Set the type that each result object is expected to match.
	 * <p>If not specified, the column value will be exposed as
	 * returned by the JDBC driver.
	 */
	public void setRequiredType(Class<T> requiredType) {
		this.requiredType = requiredType;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public T mapRow(ResultSet rs, int row) throws SQLException {
		if (BaseEntity.class.isAssignableFrom(this.requiredType)){
			return (T)BaseEntity.loadFromResultSet((Class<? extends BaseEntity>)this.requiredType, rs);
		}
		else {
			if (propertyMap==null){
				propertyMap = new HashMap<String,PropertyDescriptor>();
				PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(this.requiredType);
				for (PropertyDescriptor pd : pds){
					propertyMap.put(pd.getName().toLowerCase(), pd);
				}
			}
			T entity;
			try {
				entity= this.requiredType.newInstance();
			}catch (Exception e){
				throw new RuntimeException("Cann't create instance for : " + this.requiredType.getName());
			}
			ResultSetMetaData md = rs.getMetaData();
			for (int col=1,colcount=md.getColumnCount();col<=colcount;col++){
				String columnName = md.getColumnLabel(col);
				PropertyDescriptor pd = propertyMap.get(columnName.toLowerCase());
				if (pd!=null && pd.getWriteMethod()!=null){
					Object value = DbUtil.readObject(rs, columnName, pd.getPropertyType());
					Method writeMethod = pd.getWriteMethod();
					if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
						writeMethod.setAccessible(true);
					}
					try {
						writeMethod.invoke(entity, value);
					} catch (Exception e) {
						throw new RuntimeException("Set property error",e);
					}
				}
			}
			return entity;
		}
	}

}
