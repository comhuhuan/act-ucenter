/**
 * @Title: selectUtil.java 
 * @Package com.act.web.util 
 * @Description: select组件查询条件
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-15 下午3:44:28
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import java.io.Serializable;

public class SelectUtil<T> implements Serializable {
	private T value;
	private String label;

	public SelectUtil(T value, String label) {
		this.value = value;
		this.label = label;
	}

	public SelectUtil() {
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
