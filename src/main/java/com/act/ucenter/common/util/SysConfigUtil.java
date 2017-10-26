/**
 * @Title: SysConfigUtil.java 
 * @Package com.act.web.util 
 * @Description: 获取配置
 * @date 2017-6-9 上午11:13:19
 * @version V1.0   
 */
package com.act.ucenter.common.util;

import com.act.web.module.dnsm.service.SystemConfigService;

import java.util.Map;

public class SysConfigUtil {

	/**
	 * 研究院标识
	 */
	public static final String SMMS_FLAG = "smms_flag";

	/**
	 * 管局标识
	 */
	public static final String CENTER_FLAG = "center_flag";

	/**
	 * 管局分配递归机构 dnsId
	 */
	public static final String SMMS_RECUR_DNSID = "isms_recur_dnsId";
	
	/**
	 * 管局分配权威机构 dnsId
	 */
	public static final String SMMS_AUTH_DNSID = "isms_auth_dnsId";
	
	/**
	 * 管局分配域名机构 dnsId
	 */
	public static final String SMMS_DOMAIN_DNSID = "isms_domain_dnsId";

	/**
	 * 管局接口版本
	 */
	public static final String SMMS_VERSION = "smms_version";

	/**
	 * 对称加密算法模式 0：不进行加密，明文传输.1：AES加密算法。
	 */
	public static final String ISMS_ENCODE = "isms_encode";

	/**
	 * 是否进行zip压缩
	 */
	public static final String ISMS_ZIP = "isms_zip";

	/**
	 * hash算法
	 */
	public static final String ISMS_HASH = "isms_hash";

	/**
	 * 加密秘钥
	 */
	public static final String ISMS_PAD_KEY = "isms_pad_key";

	/**
	 * 偏移量
	 */
	public static final String ISMS_PASS_PY = "isms_pass_py";

	/**
	 * 认证密钥
	 */
	public static final String ISMS_MSG_KEY = "isms_msg_key";

	/**
	 * sftp ip地址
	 */
	public static final String ISMS_SFTP_IP = "isms_sftp_ip";
	/**
	 * sftp 用户名
	 */
	public static final String ISMS_SFTP_USER = "isms_sftp_user";
	/**
	 * sftp 密码
	 */
	public static final String ISMS_SFTP_PASSWORD = "isms_sftp_password";
	/**
	 * sftp 端口
	 */
	public static final String ISMS_SFTP_PORT = "isms_sftp_port";
	/**
	 * sftp 基础数据上传地址
	 */
	public static final String ISMS_SFTP_BASIC = "basic_path";

	/**
	 * 单位属性code
	 */
	public static final String DWSX_CODE_TYPE = "dwxz";
	
	/**
	 * 证件类型
	 */
	public static final String ZJLX_CODE_TYPE = "zjlx";
	

	private static SystemConfigService systemConfigService = SpringContextUtil
			.getBean("systemConfigServiceImpl");

	/**
	 * @Title: loadConfig
	 * @Description:根据tab_sys_config 的configId 字段 返回configval值
	 * @param configId
	 * @throws
	 * @create 2017-6-9 下午12:36:20
	 * @update 2017-6-9 下午12:36:20
	 */
	public static String loadConfig(String configId) {
		Map<String, String> resultMap = systemConfigService.loadConfig();
		String result = resultMap.get(configId);
		return result;
	}
}
