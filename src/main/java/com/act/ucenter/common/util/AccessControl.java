package com.act.ucenter.common.util;

import java.util.ArrayList;
import java.util.List;

public class AccessControl {
	/**
	 * 权限控制列的母表
	 */
	private String tableName;
	/**
	 * 权限控制列的名称
	 */
	private String columnName;
	
	private List<?> values = new ArrayList<Object>();

	public AccessControl(String tableName,String columnName,List<?> values){
		this.tableName = tableName;
		this.columnName = columnName;
		this.values = values;
	}
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<?> getValues() {
		return values;
	}

	public void setValues(List<?> values) {
		this.values = values;
	}
	
	
	
}
