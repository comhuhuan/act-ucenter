package com.act.ucenter.common.util;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

	private Integer pageIndex;// 每页第一行 index
	private Integer pageSize;// 每页记录数
	private List<T> rows = new ArrayList<T>();// 当前页结果集
	private long total;// 总记录数
	private int totalPageCount;// 总页数

	public PageResult() {

	}

	public PageResult(List<T> rows, int pageIndex, int pageSize,
			int totalPageCount, long total) {
		Assert.notNull(rows);
		this.rows.addAll(rows);
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalPageCount = totalPageCount;
		this.total = total;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}


}
