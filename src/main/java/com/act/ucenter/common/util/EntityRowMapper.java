package com.act.ucenter.common.util;

import com.act.framework.entity.BaseEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper<T extends BaseEntity> implements org.springframework.jdbc.core.RowMapper<T>{
	private Class<T> requiredType;
	
	public EntityRowMapper(Class<T> requiredType){
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

	
	@Override
	public T mapRow(ResultSet rs, int row) throws SQLException {
		return BaseEntity.loadFromResultSet(this.requiredType, rs);

	}

}
