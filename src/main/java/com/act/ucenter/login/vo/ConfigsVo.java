/**
 * @Title: ConfigListVo.java 
 * @Package com.act.web.module.dnsm.vo 
 * @Description: 存放需更新的配置
 * @author   fmj
 * @modifier fmj
 * @date 2017-6-9 上午10:32:16
 * @version V1.0   
 */
package com.act.ucenter.login.vo;

import java.util.List;

public class ConfigsVo {

	private List<String> sysList;

	public List<String> getSysList() {
		return sysList;
	}

	public void setSysList(List<String> sysList) {
		this.sysList = sysList;
	}

}
