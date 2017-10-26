/**
 * @Title: CompanyAckUtil.java 
 * @Package com.act.web.util 
 * @Description: 企业Ack 结果处理
 * @author   fmj
 * @modifier fmj
 * @date 2017-8-22 下午3:49:37
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.module.dnsm.vo.AckResultVo;

import java.util.ArrayList;
import java.util.List;

public class CompanyAckUtil {

	private static final String SCC_FLAG = "0";

	private static final String ERR_FLAG = "1";

	private static final String PART_FLAG = "2";

	/**
	 * @Title: getCmdFlagByAck
	 * @Description: 根据ack列表 返回同步企业状态 0-同步成功 1-同步失败 3-部分失败
	 * @create 2017-8-22 下午3:31:30
	 * @update 2017-8-22 下午3:31:30
	 */
	public static String getCmdFlagByAck(List<AckResultVo> acks) {

		List<String> resultCodes = new ArrayList<String>();
		for (AckResultVo ack : acks) {
			resultCodes.add(ack.getResultCode());
		}

		if (ValidatorUtil.validateListEmpty(acks)) {
			if (resultCodes.contains(SCC_FLAG)
					&& !resultCodes.contains(ERR_FLAG)) {
				return SCC_FLAG;
			}

			if (!resultCodes.contains(SCC_FLAG)
					&& resultCodes.contains(ERR_FLAG)) {
				return ERR_FLAG;
			}

			if (resultCodes.contains(SCC_FLAG)
					|| resultCodes.contains(ERR_FLAG)) {
				return PART_FLAG;
			}
		}
		return "1";
	}

}
