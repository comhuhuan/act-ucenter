package com.act.ucenter.common.util;

import org.springframework.util.StringUtils;

public class QueryColumn {
	private String columnName;
	private String columnSql;
	private String columnNameSql; //名称查询语句
	private String tableAlias;
	private Class columnClass;

	public QueryColumn(String columnName,String tableAlias,Class columnClass,String columnSql,String columnNameSql){
		this.columnName=columnName;
		this.tableAlias = tableAlias;
		this.columnClass = columnClass;
		this.columnSql = columnSql;
		this.columnNameSql = columnNameSql;
		
	}
	public QueryColumn(String columnName,String tableAlias,Class columnClass,String columnSql){
		this(columnName, tableAlias, columnClass, columnSql, null);
	}
	
	public QueryColumn(String columnName,String tableAlias,Class columnClass){
		this(columnName, tableAlias, columnClass, null, null);
	}
	public String getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(String columnSql) {
		this.columnSql = columnSql;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Class getColumnClass() {
		return columnClass;
	}

	public void setColumnClass(Class columnClass) {
		this.columnClass = columnClass;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}
	
	

	public String getColumnNameSql() {
		return columnNameSql;
	}

	public void setColumnNameSql(String columnNameSql) {
		this.columnNameSql = columnNameSql;
	}

	public String getSql(){
		if (this.columnSql!=null&&this.columnSql.length()>0){
			return this.columnSql;
		}
		else if (this.tableAlias!=null&&this.tableAlias.length()>0){
			return this.tableAlias + "." + this.columnName;
		}
		else {
			return this.columnName;
				
		}
	}
	
	public String getNameSql() {
		String nameSql = this.columnNameSql;
		if (nameSql!=null&&nameSql.indexOf("@BaseColumn@")>=0){
			nameSql = StringUtils.replace(nameSql, "@BaseColumn@", getSql());
		}
		return nameSql;
	}
	
	public boolean isNameColumn() {
		return this.columnNameSql!=null && columnNameSql.length()>0;
	}
	
	public boolean isVirtualColumn() {
		return this.columnSql!=null&&this.columnSql.length()>0;
	}
}
