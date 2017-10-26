package com.act.ucenter.common.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Map;

public class RowSetQueryResult {
	private long total;
	private SqlRowSet rows;
	private Map<String,Object> sumRow;
	private List<QueryColumn> columns;

	public RowSetQueryResult(long total, SqlRowSet rows,List<QueryColumn> columns) {
		this.total = total;
		this.rows = rows;
		this.columns = columns;
	}

	public RowSetQueryResult(long total, SqlRowSet rows,Map<String,Object> sumRow,List<QueryColumn> columns) {
		this.total = total;
		this.rows = rows;
		this.sumRow = sumRow;
		this.columns = columns;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@JsonSerialize()
	public SqlRowSet getRows() {
		return rows;
	}

	public void setRows(SqlRowSet rows) {
		this.rows = rows;
	}
	
	public Map<String,Object> getSumRow(){
		return sumRow;
	}

	public List<QueryColumn> getColumns() {
		return columns;
	}


}
