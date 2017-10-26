/**
 * @Title: FilterSendCompanyUtil.java 
 * @Package com.act.web.util 
 * @Description: 过滤下发企业工具类
 * @author   fmj
 * @modifier fmj
 * @date 2017-7-25 上午11:34:30
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.dnsm.model.DnsAuthInstruct;
import com.act.dnsm.model.DnsDomainInstruct;
import com.act.dnsm.model.DnsRecurInstruct;
import com.act.web.module.dnsm.service.DNSCommandAckService;
import com.act.web.module.dnsm.vo.AckResultVo;
import com.act.web.module.dnsm.vo.SendCompanyVo;

import java.util.List;

public class FilterSendCompanyUtil {

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
	 * 过滤操作删除
	 */
	private final static Integer OPERAT_TYPE_DEL = 1;

	/**
	 * 过滤指令 未下发到企业
	 */
	private final static String CMD_TO_COMPANY_ERR = "1";

	/**
	 * 过滤指令 下发到企业
	 */
	private final static String CMD_TO_COMPANY = "0";

	/**
	 * 研究员下发指令
	 */
	private final static String REMARK_SMMS = "研究院指令";

	/**
	 * 过期下发企业备注
	 */
	private final static String REMARK_EXP = "过期删除";

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
	 * 
	 * @Title: recurInstruct
	 * @Description: 递归企业
	 * @create 2017-7-25 下午5:10:58
	 * @update 2017-7-25 下午5:10:58
	 */
	private static void recurInstruct() throws Exception {
		List<DnsRecurInstruct> recurInstructs = DbUtil
				.queryForObjectList(
						" select * from dns_recur_instruct where commandType = 3 and  operationType = 0 and (cmdtocompany = 1 or cmdtocompanydel = 1) ",
						DnsRecurInstruct.class);
		for (DnsRecurInstruct recurInstruct : recurInstructs) {
			if (CMD_TO_COMPANY_ERR.equals(recurInstruct.getCmdtocompany())
					&& DateUtil.compareDate(recurInstruct.getEffecttime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						recurInstruct);
				sendVo.setRemark(REMARK_SMMS);
				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), RECUR_COMPANY_TYPE,
						DNS_COMMAND_TYPE);
				DbUtil.update(
						" update dns_recur_instruct set cmdtocompany = ? where commandId = ? ",
						CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
			}

			if (CMD_TO_COMPANY_ERR.equals(recurInstruct.getCmdtocompanydel())
					&& DateUtil.compareDate(recurInstruct.getExpiredtime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						recurInstruct);
				sendVo.setOperationType(OPERAT_TYPE_DEL);
				sendVo.setRemark(REMARK_EXP);
				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), RECUR_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update dns_recur_instruct set cmdtocompanydel = ? where commandId = ? ",
							CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
				}

			}

		}

	}

	/**
	 * @Title: authInstruct
	 * @Description: 权威企业
	 * @create 2017-7-25 下午5:11:10
	 * @update 2017-7-25 下午5:11:10
	 */
	private static void authInstruct() throws Exception {
		List<DnsAuthInstruct> authInstructs = DbUtil
				.queryForObjectList(
						" select * from dns_auth_instruct where commandType = 3 and  operationType = 0 and (cmdtocompany = 1 or cmdtocompanydel = 1) ",
						DnsAuthInstruct.class);
		for (DnsAuthInstruct authInstruct : authInstructs) {
			if (CMD_TO_COMPANY_ERR.equals(authInstruct.getCmdtocompany())
					&& DateUtil.compareDate(authInstruct.getEffecttime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						authInstruct);

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), AUTH_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				DbUtil.update(
						" update dns_auth_instruct set cmdtocompany = ? where commandId = ? ",
						CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
			}

			if (CMD_TO_COMPANY_ERR.equals(authInstruct.getCmdtocompanydel())
					&& DateUtil.compareDate(authInstruct.getExpiredtime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						authInstruct);
				sendVo.setOperationType(OPERAT_TYPE_DEL);
				sendVo.setRemark(REMARK_EXP);
				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), AUTH_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update dns_auth_instruct set cmdtocompanydel = ? where commandId = ? ",
							CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
				}
			}

		}

	}

	/**
	 * @Title: domainInstruct
	 * @Description: 域名企业
	 * @create 2017-7-25 下午5:11:23
	 * @update 2017-7-25 下午5:11:23
	 */
	private static void domainInstruct() throws Exception {
		List<DnsDomainInstruct> domainInstructs = DbUtil
				.queryForObjectList(
						" select * from dns_domain_instruct where commandType = 3 and  operationType = 0 and (cmdtocompany = 1 or cmdtocompanydel = 1) ",
						DnsDomainInstruct.class);
		for (DnsDomainInstruct domainInstruct : domainInstructs) {
			if (CMD_TO_COMPANY_ERR.equals(domainInstruct.getCmdtocompany())
					&& DateUtil.compareDate(domainInstruct.getEffecttime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						domainInstruct);

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), DOMAIN_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				DbUtil.update(
						" update dns_domain_instruct set cmdtocompany = ? where commandId = ? ",
						CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
			}

			if (CMD_TO_COMPANY_ERR.equals(domainInstruct.getCmdtocompanydel())
					&& DateUtil.compareDate(domainInstruct.getExpiredtime(),
							DEFAULT_DATE_FORMAT)) {
				SendCompanyVo sendVo = new SendCompanyVo(
						domainInstruct);
				sendVo.setOperationType(OPERAT_TYPE_DEL);
				sendVo.setRemark(REMARK_EXP);

				List<AckResultVo> result = dnsCommandAckService.commandToCompany(
						getSendCompanyXml(sendVo), DOMAIN_COMPANY_TYPE,
						DNS_COMMAND_TYPE);

				if (CMD_TO_COMPANY.equals(CompanyAckUtil.getCmdFlagByAck(result))) {
					DbUtil.update(
							" update dns_domain_instruct set cmdtocompanydel = ? where commandId = ? ",
							CompanyAckUtil.getCmdFlagByAck(result), sendVo.getCommandId());
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
