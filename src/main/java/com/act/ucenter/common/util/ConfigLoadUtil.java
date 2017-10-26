/**   
 * @Title: ConfigLoadUtil.java 
 * @Package com.act.web.util 
 * @Description: (加载config.properties) 
 * @author   fmj
 * @date 2017-3-4 上午9:06:30 
 * @version V1.0   
 */
package com.act.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigLoadUtil {
	@Value("${loginCode}")
	private String loginCode;

	@Value("${statisticsDay}")
	private String statisticsDay;

	@Value("${xssTip}")
	private String xssTip;

	@Value("${table_schema}")
	private String tableSchema;

	@Value("${ircs_ticket}")
	private String ircsTicket;

	@Value("${virtual_house_code}")
	private String virtualHouseCode;

	@Value("${gOpenalarmUser}")
	private String gOpenalarmUser;

	@Value("${sysName}")
	private String sysName;

	@Value("${subSystem}")
	private String subSystem;

	@Value("${tomcat_port}")
	private String tomcat_port;


	public String getLoginCode() {
		return loginCode;
	}

	public void setLoginCode(String loginCode) {
		this.loginCode = loginCode;
	}

	public String getStatisticsDay() {
		return statisticsDay;
	}

	public void setStatisticsDay(String statisticsDay) {
		this.statisticsDay = statisticsDay;
	}

	public String getXssTip() {
		return xssTip;
	}

	public void setXssTip(String xssTip) {
		this.xssTip = xssTip;
	}

	public String getTableSchema() {
		return tableSchema;
	}

	public void setTableSchema(String tableSchema) {
		this.tableSchema = tableSchema;
	}

	public String getIrcsTicket() {
		return ircsTicket;
	}

	public void setIrcsTicket(String ircsTicket) {
		this.ircsTicket = ircsTicket;
	}

	public String getVirtualHouseCode() {
		return virtualHouseCode;
	}

	public void setVirtualHouseCode(String virtualHouseCode) {
		this.virtualHouseCode = virtualHouseCode;
	}

	public String getgOpenalarmUser() {
		return gOpenalarmUser;
	}

	public void setgOpenalarmUser(String gOpenalarmUser) {
		this.gOpenalarmUser = gOpenalarmUser;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getTomcat_port() {
		return tomcat_port;
	}

	public void setTomcat_port(String tomcat_port) {
		this.tomcat_port = tomcat_port;
	}

}
