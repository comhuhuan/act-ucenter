package com.act.ucenter.common.util;

import java.util.List;

/**
 * 查询结果
 * 
 * @author james
 *
 */
public class QueryResult<T> {
	private int total;
	private List<T> rows;

	public QueryResult() {

	}

	public QueryResult(int total,List<T> rows) {
		this.total = total;
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}



}
