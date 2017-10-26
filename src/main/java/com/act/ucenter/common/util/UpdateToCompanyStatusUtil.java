package com.act.ucenter.common.util;

import org.apache.log4j.Logger;


public class UpdateToCompanyStatusUtil {
	private static Logger log = Logger.getLogger(UpdateToCompanyStatusUtil.class);
	/**
	 * 根据企业ID，操作类型，以及指令ID，更新对应指令表中转发给企业的状态
	 * @param dnsId 企业id
	 * @param resultCode 调用企业接口的返回结果
	 * @param commandType 指令类型
	 * @param operationtype 操作类型，0—新增1—删除
	 * @param commandId 指令ID
	 */
	public static void updateTable(String resultCode,String dnsId,
			String commandId,Integer commandType,Integer operationtype ){
		String recurUserId = SysConfigUtil.loadConfig("isms_recur_dnsId");//递归机构
		String authUserId = SysConfigUtil.loadConfig("isms_auth_dnsId");//权威机构
		String domainUserId = SysConfigUtil.loadConfig("isms_domain_dnsId");//域名注册机构
		//递归模块
		if(dnsId.equals(recurUserId)){
			try{
				switch (commandType) {
				case 1://监测指令
					DbUtil.update(" update dns_recur_instruct set cmdtocompany = ? where commandId = ? ",
							resultCode, commandId);
					break;
				case 2://特定域名处置指令
					if(operationtype.equals(0)){
						DbUtil.update(" update dns_recur_instruct set cmdtocompany = ? where commandId = ? ",
								resultCode, commandId);
					}else if(operationtype.equals(1)){
						DbUtil.update(" update dns_recur_instruct set cmdtocompanydel = ? where commandId = ? ",
								resultCode, commandId);
					}
					break;
				case 3://黑名单指令
					DbUtil.update(" update dns_recur_instruct set cmdtocompany = ? where commandId = ? ",
							resultCode, commandId);
					break;
				case 9://域名递归信息查询指令
					DbUtil.update(" update dns_recur_parseinfoquery set cmdtocompany = ? where commandId = ? ",
							resultCode, commandId);
					break;
				default:
					break;
				}
			}catch (Exception e) {
				log.error("更新表出错", e);
			}
		}
		
		//权威模块
		if(dnsId.equals(authUserId)){
			try{
				switch (commandType) {
				case 2://特定域名处置指令
					if(operationtype.equals(0)){
						DbUtil.update(" update dns_auth_instruct set cmdtocompany = ? where commandId = ? ",
								resultCode, commandId);
					}else if(operationtype.equals(1)){
						DbUtil.update(" update dns_auth_instruct set cmdtocompanydel = ? where commandId = ? ",
								resultCode, commandId);
					}
					break;
				case 3://黑名单指令
					DbUtil.update(" update dns_auth_instruct set cmdtocompany = ? where commandId = ? ",
							resultCode, commandId);
					break;
				default:
					break;
				}
			}catch (Exception e) {
				log.error("更新表出错", e);
			}
		}
		//注册模块
		if(dnsId.equals(domainUserId)){
			try{
				switch (commandType) {
				case 2://特定域名处置指令
					if(operationtype.equals(0)){
						DbUtil.update(" update dns_domain_instruct set cmdtocompany = ? where commandId = ? ",
								resultCode, commandId);
					}else if(operationtype.equals(1)){
						DbUtil.update(" update dns_domain_instruct set cmdtocompanydel = ? where commandId = ? ",
								resultCode, commandId);
					}
					break;
				case 3://黑名单指令
					DbUtil.update(" update dns_domain_instruct set cmdtocompany = ? where commandId = ? ",
							resultCode, commandId);
					break;
				default:
					break;
				}
			}catch (Exception e) {
				log.error("更新表出错", e);
			}
		}
	}
}
