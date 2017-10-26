/**
 * @Title: SuperviseSendCompanyUtil.java 
 * @Package com.act.web.util 
 * @Description: 监管指令下发至企业工具类
 * @author   fmj
 * @modifier fmj
 * @date 2017-7-28 下午4:41:16
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.dnsm.model.TAuthInstruct;
import com.act.dnsm.model.TDomainRegisterInstruct;
import com.act.dnsm.model.TRecurInstruct;
import com.act.web.module.dnsm.service.DNSCommandAckService;
import com.act.web.module.dnsm.vo.AckResultVo;
import com.act.web.module.dnsm.vo.SendCompanyVo;

import java.util.List;

public class SuperviseSendCompanyUtil {

	/**
	 * 单位类型 递归
	 */
	private final static String RECUR_COMPANY_TYPE = "1";

	/**
	 * 单位类型 权威
	 */
	private final static String AUTH_COMPANY_TYPE = "2";

	/**
	 * 单位类型 域名
	 */
	private final static String DOMAIN_COMPANY_TYPE = "3";

	/**
	 * 1-黑名单网站列表指令 2-特定域名过滤指令 3-域名递归解析信息查询指令
	 */
	private final static Integer DNS_COMMAND_TYPE = 2;

	/**
	 * 指令操作新增
	 */
	private final static Integer OPERAT_TYPE_ADD = 0;

	/**
	 * 过滤指令 下发到企业
	 */
	private final static String CMD_TO_COMPANY = "0";

	/**
	 * 安全中心指令
	 */
	private final static String REMARK_EXP = "国家安全中心指令";

	/**
	 * 下发企业备注
	 */
	private final static String REMARK_DEL = "上级指令下发删除";

	/**
	 * 日期格式
	 */
	private static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private static DNSCommandAckService dnsCommandAckService = SpringContextUtil
			.getBean("DNSCommandAckServiceImpl");

	/**
	 * @Title: sendCompany
	 * @Description: 下发企业信息
	 * @create 2017-7-25 下午3:20:14
	 * @update 2017-7-25 下午3:20:14
	 */
	public static void sendCompany() throws Exception {
		recurInstruct();
		authInstruct();
		domainInstruct();
	}

	/**
	 * @Title: recurInstruct
	 * @Description: 递归企业
	 * @create 2017-7-28 下午4:48:45
	 * @update 2017-7-28 下午4:48:45
	 */
	private static void recurInstruct() throws Exception {
		List<TRecurInstruct> recurInstructs = DbUtil.queryForObjectList(
				" select * from t_recur_instruct where cmdtocompany = 1 ",
				TRecurInstruct.class);

		for (TRecurInstruct tRecurInstruct : recurInstructs) {
			//999指令需要企业自己手动处理，不转发
			if(tRecurInstruct.getRule()==999){
				continue;
			}
			tRecurInstruct.getEtype();
			if (DateUtil.compareDate(tRecurInstruct.getEffectivedate(),
					DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						tRecurInstruct);
				if (OPERAT_TYPE_ADD.equals(sendVo.getOperationType())) {
					sendVo.setRemark(REMARK_EXP);
				} else {
					sendVo.setRemark(REMARK_DEL);
				}

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), RECUR_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update t_recur_instruct set cmdtocompany = ? where id = ? ",
							CMD_TO_COMPANY, tRecurInstruct.getId());
				}
			}
		}

	}

	/**
	 * @Title: authInstruct
	 * @Description: 权威企业
	 * @create 2017-7-28 下午4:48:45
	 * @update 2017-7-28 下午4:48:45
	 */
	private static void authInstruct() throws Exception {
		List<TAuthInstruct> authInstructs = DbUtil.queryForObjectList(
				" select * from t_auth_instruct where cmdtocompany = 1 ",
				TAuthInstruct.class);

		for (TAuthInstruct tAuthInstruct : authInstructs) {
			//999指令需要企业自己手动处理，不转发
			if(tAuthInstruct.getRule()==999){
				continue;
			}
			if (DateUtil.compareDate(tAuthInstruct.getEffectivedate(),
					DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						tAuthInstruct);
				if (OPERAT_TYPE_ADD.equals(sendVo.getOperationType())) {
					sendVo.setRemark(REMARK_EXP);
				} else {
					sendVo.setRemark(REMARK_DEL);
				}

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), AUTH_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update t_auth_instruct set cmdtocompany = ? where id = ? ",
							CMD_TO_COMPANY, tAuthInstruct.getId());
				}
			}
		}

	}

	/**
	 * @Title: domainInstruct
	 * @Description: 域名企业
	 * @create 2017-7-28 下午4:48:45
	 * @update 2017-7-28 下午4:48:45
	 */
	private static void domainInstruct() throws Exception {
		List<TDomainRegisterInstruct> domainInstructs = DbUtil
				.queryForObjectList(
						" select * from t_domain_register_instruct where cmdtocompany = 1 ",
						TDomainRegisterInstruct.class);

		for (TDomainRegisterInstruct tdomainInstruct : domainInstructs) {
			//999指令需要企业自己手动处理，不转发
			if(tdomainInstruct.getRule()==999){
				continue;
			}
			if (DateUtil.compareDate(tdomainInstruct.getEffectivedate(),
					DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						tdomainInstruct);
				if (OPERAT_TYPE_ADD.equals(sendVo.getOperationType())) {
					sendVo.setRemark(REMARK_EXP);
				} else {
					sendVo.setRemark(REMARK_DEL);
				}

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), DOMAIN_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update t_domain_register_instruct set cmdtocompany = ? where id = ? ",
							CMD_TO_COMPANY, tdomainInstruct.getId());
				}
			}
		}

	}

	/**
	 * @Title: getSendCompanyXml
	 * @Description: 根据vo得到下发企业过滤的xml
	 * @param xmlVo
	 *            初始化的下发企业xmlVo
	 * @create 2017-7-25 上午11:41:39
	 * @update 2017-7-25 上午11:41:39
	 */
	private static String getSendCompanyXml(SendCompanyVo xmlVo) {
		StringBuffer sb = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		sb.append("<command>");
		sb.append("<commandId>").append(xmlVo.getCommandId())
				.append("</commandId>");// 管理指令id
		sb.append("<type>").append(xmlVo.getType()).append("</type>");// 处置类型
		sb.append("<domain>").append(xmlVo.getDomain()).append("</domain>");// 域名
		sb.append("<urgency>").append(xmlVo.getUrgency()).append("</urgency>");// 紧急程度

		sb.append("<range>");
		sb.append("<dnsId>").append(xmlVo.getDnsId()).append("</dnsId>");// 指令生效的域名服务机构
		sb.append("<effectiveScope>").append(xmlVo.getEffectiveScope())
				.append("</effectiveScope>");// 生效区域
		sb.append("</range>");

		sb.append("<operationType>").append(xmlVo.getOperationType())
				.append("</operationType>");// 操作类型
		sb.append("<timeStamp>").append(xmlVo.getTimeStamp())
				.append("</timeStamp>");// 生成时间
		sb.append("<remark>").append(xmlVo.getRemark()).append("</remark>");// 备注
		sb.append("</command>");
		return sb.toString();
	}
}
