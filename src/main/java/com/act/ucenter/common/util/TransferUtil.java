/**
 * @Title: TransferUtil.java 
 * @Package com.act.web.util 
 * @Description: 穿梭框选择器
 * @author   fmj
 * @modifier fmj
 * @date 2017-7-9 下午3:42:55
 * @version V1.0   
 */
package com.act.ucenter.common.util;

public class TransferUtil<T> {
	private T key;
	private String label;
	private boolean disabled;

	public T getKey() {
		return key;
	}

	public void setKey(T key) {
		this.key = key;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

}
