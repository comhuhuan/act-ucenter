/**
 * @Title: CascaderUtil.java 
 * @Package com.act.web.util 
 * @Description: 联级下拉框 
 * @author   fmj
 * @modifier fmj
 * @date 2017-6-26 下午4:07:56
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import java.io.Serializable;
import java.util.List;

public class CascaderUtil<T> implements Serializable {
	private T value;
	private String label;
	private List<CascaderUtil<T>> children;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<CascaderUtil<T>> getChildren() {
		return children;
	}

	public void setChildren(List<CascaderUtil<T>> children) {
		this.children = children;
	}

}
